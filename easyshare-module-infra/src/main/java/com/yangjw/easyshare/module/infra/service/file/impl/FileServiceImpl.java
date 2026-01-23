package com.yangjw.easyshare.module.infra.service.file.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.yangjw.easyshare.framework.common.exception.ServiceException;
import com.yangjw.easyshare.module.infra.config.properties.UploadProperties;
import com.yangjw.easyshare.module.infra.enums.InfraErrorCodeConstants;
import com.yangjw.easyshare.module.infra.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final UploadProperties uploadProperties;

    @Override
    public String upload(MultipartFile file) {
        // 验证文件
        String ext = validateFile(file);
        // 构建文件路径
        String datePath = buildFilePath();
        // 构建文件名
        String newFileName = IdUtil.fastSimpleUUID() + "." + ext;

        // 相对路径（存数据库）
        String relativePath = "/uploads/" + datePath + "/" + newFileName;

        // 保存文件
        saveToLocal(file, relativePath);

        // 返回相对路径（不带域名，避免换服务器失效）
        return relativePath;
    }

    private String validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ServiceException(InfraErrorCodeConstants.FILE_NOT_EMPTY);
        }
        if (file.getSize() > uploadProperties.getMaxSize()) {
            throw new ServiceException(InfraErrorCodeConstants.FILE_TOO_LARGE);
        }

        String originalFilename = file.getOriginalFilename();
        String ext = FileNameUtil.extName(originalFilename);
        if (StrUtil.isBlank(ext)) {
            throw new ServiceException(InfraErrorCodeConstants.FILE_NOT_SUPPORT);
        }

        ext = ext.toLowerCase();
        if (!uploadProperties.getAllowExt().contains(ext)) {
            throw new ServiceException(InfraErrorCodeConstants.FILE_NOT_SUPPORT);
        }
        return ext;
    }

    private String buildFilePath() {
        DateTime now = DateUtil.date();
        return DateUtil.format(now, "yyyy") + "/"
                + DateUtil.format(now, "MM") + "/"
                + DateUtil.format(now, "dd");
    }

    private void saveToLocal(MultipartFile file, String relativePath) {
        // relativePath: /uploads/yyyy/MM/dd/uuid.png
        // 磁盘路径: basePath/yyyy/MM/dd/uuid.png
        String subPath = StrUtil.removePrefix(relativePath, "/uploads/");
        String saveFilePath = FileUtil.normalize(uploadProperties.getBasePath() + "/" + subPath);

        String saveDirPath = FileUtil.getParent(saveFilePath, 1);
        FileUtil.mkdir(saveDirPath);

        try {
            file.transferTo(new File(saveFilePath));
        } catch (IOException e) {
            throw new ServiceException(InfraErrorCodeConstants.FILE_SAVE_ERROR);
        }
    }

    /**
     * 构建对外访问的完整 URL
     *
     * @param relativePath 相对路径，例如：/uploads/2026/01/22/uuid.png
     */
    @Override
    public String buildAccessUrl(String relativePath) {
        String baseUrl = StrUtil.removeSuffix(uploadProperties.getBaseUrl(), "/");
        String path = StrUtil.startWith(relativePath, "/") ? relativePath : "/" + relativePath;
        return baseUrl + path;
    }
}