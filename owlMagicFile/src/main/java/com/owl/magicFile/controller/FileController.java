package com.owl.magicFile.controller;

import com.owl.comment.annotations.OwlLogInfo;
import com.owl.magicFile.service.OMFileService;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.util.RegexUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * 文件控製類
 * @author engwen
 * email xiachanzou@outlook.com
 * 2017/7/13.
 */
@OwlLogInfo
@Controller
@RequestMapping("file")
public class FileController {
    @Resource
    private OMFileService fileService;

    /**
     * 多文件上传 指定 from 表單屬性 enctype="multipart/form-data" ，指定from表單中待提交文件name為 files
     * @param files
     * @return
     */
    @ResponseBody
    @RequestMapping("/uploadFilesByFrom")
    public MsgResultVO uploadFilesByFrom(@RequestParam("files") MultipartFile[] files) {
        MsgResultVO result;
        if (null != files && files.length > 0) {
            result = fileService.uploadFilesByFrom(files);
        } else {
            result = new MsgResultVO();
            result.errorResult(MsgConstant.REQUEST_PARAMETER_ERROR);
        }
        return result;
    }

    /**
     * 单文件上传 指定 from 表單屬性 enctype="multipart/form-data" ，指定from表單中待提交文件name為 file
     * @param file
     * @return
     */
    @ResponseBody
    @RequestMapping("/uploadFileByFrom")
    public MsgResultVO uploadFileByFrom(@RequestParam("file") MultipartFile file) {
        MsgResultVO result;
        if (!file.isEmpty()) {
            result = fileService.uploadFileByFrom(file);
        } else {
            result = new MsgResultVO();
            result.errorResult(MsgConstant.REQUEST_PARAMETER_ERROR);
        }
        return result;
    }

    /**
     * 使用base64上传文件
     * @param byBase64
     * @return
     */
    @ResponseBody
    @RequestMapping("/uploadFileByBase64")
    public MsgResultVO uploadFileByBase64(String byBase64) {
        MsgResultVO result;
        if (RegexUtil.isEmpty(byBase64)) {
            result = new MsgResultVO();
            result.errorResult(MsgConstant.REQUEST_PARAMETER_ERROR);
        } else {
            result = fileService.uploadFileByBase64(byBase64);
        }
        return result;
    }

    /**
     * 下载
     * @param response
     * @param md5
     */
    @ResponseBody
    @RequestMapping("/download")
    public void download(HttpServletResponse response, String md5) {
        File file = fileService.selectByMD5(md5);
        if (file == null || !file.exists()) {
            return;
        }
        try {
            FileInputStream in = new FileInputStream(file);
            OutputStream out = response.getOutputStream();
            byte buffer[] = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            System.out.print(e);
        }
    }
}
