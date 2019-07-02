package com.bysj.manage.service;

import com.bysj.common.vo.PicUploadResult;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    PicUploadResult uploadFile(MultipartFile uploadFile);
}
