package com.yangjw.easyshare.module.infra.controller.file;

import com.yangjw.easyshare.framework.common.pojo.CommonResult;
import com.yangjw.easyshare.module.infra.service.file.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Tag(name = "基础模块 - 文件")
@RestController
@RequestMapping("/infra/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @Operation(summary = "上传文件")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResult<String> upload(@RequestPart("file")  MultipartFile file) {
        String url = fileService.upload(file);
        return CommonResult.success(url);
    }
}
