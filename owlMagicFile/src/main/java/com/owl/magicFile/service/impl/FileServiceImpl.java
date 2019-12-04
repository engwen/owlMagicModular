package com.owl.magicFile.service.impl;


import com.owl.magicFile.dao.OMFileDao;
import com.owl.magicFile.model.OMFile;
import com.owl.magicFile.service.OMFileService;
import com.owl.magicFile.vo.OMFileVO;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.mvc.vo.PageVO;
import com.owl.util.LogPrintUtil;
import com.owl.util.MD5Util;
import com.owl.util.PropertiesUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2017/7/11.
 */
@Service("fileService")
public class FileServiceImpl implements OMFileService {
    private static final String uploadPath = PropertiesUtil.readProperties("file_setting","upload.path.dir");
    private static final String cachePath = PropertiesUtil.readProperties("file_setting","cache.path.dir");

    @Resource
    OMFileDao fileDao;


    /**
     * 下載文件
     * @param md5
     * @return
     */
    public File selectByMD5(String md5) {
        LogPrintUtil.info("request selectByMD5 file");
        File result = null;
        OMFile file = fileDao.selectByMD5(md5);
        if (null != file) {
            result = new File(uploadPath + File.separatorChar + md5);
        }
        return result;
    }

    /**
     * 上傳文件
     * @param imgStr
     * @return
     */
    public MsgResultVO<OMFileVO> uploadFileByBase64(String imgStr) {
        MsgResultVO<OMFileVO> result = new MsgResultVO<>();
        OMFileVO fileVO = new OMFileVO();
        BASE64Decoder decoder = new BASE64Decoder();
        String tempName = UUID.randomUUID() + String.valueOf(new Date().getTime());
        try {
            imgStr = imgStr.split(",")[1];
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            String imgFilePath = uploadPath + File.separatorChar + tempName;//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            fileVO.setMd5(countMD5(tempName));
            result.setResultData(fileVO);
        } catch (Exception e) {
            LogPrintUtil.error("upload by base64 is error " + e);
            result.errorResult(MsgConstant.REQUEST_NO_KNOW_ERROR);
        }
        return result;
    }

    /**
     * 上傳文件
     * @param files
     * @return
     */
    public MsgResultVO<PageVO<OMFileVO>> uploadFilesByFrom(MultipartFile[] files) {
        LogPrintUtil.info("request uploadByRequest file");
        MsgResultVO<PageVO<OMFileVO>> result = new MsgResultVO<>();
        PageVO<OMFileVO> filePageVO = new PageVO<OMFileVO>();
        for (MultipartFile mf : files) {
            if (!mf.isEmpty()) {
                filePageVO.getResultData().add(uploadFileByFrom(mf).getResultData());
            }
        }
        result.successResult(filePageVO);
        return result;
    }

    public MsgResultVO<OMFileVO> uploadFileByFrom(MultipartFile file) {
        MsgResultVO<OMFileVO> result = new MsgResultVO<>();
        OMFileVO fileVO = new OMFileVO();
        try {
            String tempName = UUID.randomUUID() + String.valueOf(new Date().getTime());
            File saveFile = new File(uploadPath + File.separatorChar + tempName);
            File cachFile = new File(cachePath + File.separatorChar + file.getOriginalFilename());

            file.transferTo(saveFile);
            FileUtils.copyFile(saveFile, cachFile);

            fileVO.setMd5(countMD5(tempName));
            fileVO.setName(file.getOriginalFilename());
            fileVO.setSize(file.getSize());
            fileVO.setType(file.getContentType());
            fileVO.setUploadTime(new Date());
            result.successResult(fileVO);
        } catch (Exception e) {
            LogPrintUtil.info("uploadByRequest is error" + e);
            result.errorResult(MsgConstant.REQUEST_NO_KNOW_ERROR);
        }
        return result;
    }


    private String countMD5(String tempName) {
        File saveFile = new File(uploadPath + File.separatorChar + tempName);
        String md5 = MD5Util.getMD5(saveFile);
        File md5File = new File(uploadPath + File.separatorChar + md5);
        OMFile file = fileDao.selectByMD5(md5);
        if (null == file) {
            file = new OMFile();
            file.setMd5(md5);
            file.setUploadTime(new Date());
            fileDao.insert(file);
            saveFile.renameTo(md5File);
        } else {
            saveFile.delete();
        }
        return md5;
    }
}
