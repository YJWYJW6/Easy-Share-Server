package com.yangjw.easyshare.module.infra.service.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    /**
     * 上传文件并返回相对路径
     */
    String upload(MultipartFile file);

    /**
     * 构建文件访问的 URL
     */
    String buildAccessUrl(String relativePath);

}