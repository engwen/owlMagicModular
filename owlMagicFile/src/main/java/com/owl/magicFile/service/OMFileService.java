package com.owl.magicFile.service;


import com.owl.magicFile.vo.OMFileVO;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.mvc.vo.PageVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 文件服務類
 * @author engwen
 *         email xiachanzou@outlook.com
 *         2017/7/11.
 */
public interface OMFileService {

    //    上傳文件
    MsgResultVO<OMFileVO> uploadFileByBase64(String fileBase64);

    MsgResultVO<PageVO<OMFileVO>> uploadFilesByFrom(MultipartFile[] files);

    MsgResultVO<OMFileVO> uploadFileByFrom(MultipartFile file);

    //    查詢將要下載的文件
    File selectByMD5(String MD5);
}
