package com.bysj.manage.controller;

import com.bysj.common.vo.PicUploadResult;
import com.bysj.manage.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileController {

    @Autowired
    FileService fileService;

    //实现文件上传
    @RequestMapping("/pic/upload")
    @ResponseBody
    public PicUploadResult uploadFile(MultipartFile uploadFile) throws IllegalStateException, IOException {
        return fileService.uploadFile(uploadFile);
    }
}
