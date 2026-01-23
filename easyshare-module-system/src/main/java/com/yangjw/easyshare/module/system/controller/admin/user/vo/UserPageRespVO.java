package com.yangjw.easyshare.module.system.controller.admin.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户列表VO - response
 *
 * @author yangjw
 */
@Data
@Schema(description = "管理后台 - 获取用户列表 Request VO")
public class UserPageRespVO {

    /**
     * 用户主键ID
     */
    @Schema(description = "id", example = "1")
    private Long id;

    /**
     * 登录账号（唯一）
     */
    @Schema(description = "用户名", example = "tom")
    private String username;

    /**
     * 手机号
     */
    @Schema(description = "手机号", example = "15601691300")
    private String phone;

    /**
     * 真实姓名
     */
    @Schema(description = "真实姓名", example = "张三")
    private String realName;

    /**
     * 学校ID（关联学校表）
     */
    @Schema(description = "学校ID", example = "1")
    private Long schoolId;

    /**
     * 用户角色：1学生 2学校负责人 3超级管理员
     */
    @Schema(description = "用户角色", example = "1")
    private Integer role;

    /**
     * 状态：1正常 0禁用
     */
    @Schema(description = "状态", example = "1")
    private Integer status;

    /**
     * 认证状态：0未认证 1认证中 2已认证 3驳回
     */
    @Schema(description = "认证状态", example = "1")
    private Integer verifyStatus;

    /**
     * 最近登录时间
     */
    @Schema(description = "最近登录时间", example = "2022-02-02 00:00:00")
    private LocalDateTime lastLoginTime;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", example = "2022-02-02 00:00:00")
    private LocalDateTime createTime;

}
