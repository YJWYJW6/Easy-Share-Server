package com.yangjw.easyshare.module.system.controller.admin.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 用户列表VO - request
 *
 * @author yangjw
 */
@Data
@Schema(description = "管理后台 - 获取用户列表 Request VO")
public class UserPageReqVO {

    /** 页码，从 1 开始 */
    @NotNull(message = "页码不能为空")
    @Schema(description = "页码", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer pageNo;

    /** 每页数量 */
    @NotNull(message = "每页数量不能为空")
    @Schema(description = "每页数量", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer pageSize;

    /** 关键词：支持 username / nickname 模糊 */
    @Schema(description = "关键词", example = "tom")
    private String keyword;

    /** 手机号（精确） */
    @Schema(description = "手机号", example = "130")
    private String phone;

    /** 角色：1学生 2学校管理员 3超级管理员 */
    @Schema(description = "角色", example = "1")
    private Integer role;

    /** 状态：1正常 0禁用 */
    @Schema(description = "状态", example = "1")
    private Integer status;

    /** 认证状态：0未认证 1认证中 2已认证 3驳回 */
    @Schema(description = "认证状态", example = "0")
    private Integer verifyStatus;

    /** 学校ID（超管可传，学校管理员忽略该字段） */
    @Schema(description = "学校ID", example = "1")
    private Long schoolId;

    /** 创建时间范围 */
    @Schema(description = "创建时间范围", example = "2022-01-01 00:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeStart;

    @Schema(description = "创建时间范围", example = "2022-01-01 00:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeEnd;

    /** 最近登录时间范围 */
    @Schema(description = "最近登录时间范围", example = "2022-01-01 00:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginTimeStart;

    @Schema(description = "最近登录时间范围", example = "2022-01-01 00:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginTimeEnd;
}
