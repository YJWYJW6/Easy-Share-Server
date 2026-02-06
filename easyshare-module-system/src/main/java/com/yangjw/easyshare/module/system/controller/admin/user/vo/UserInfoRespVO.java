package com.yangjw.easyshare.module.system.controller.admin.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * 用户信息VO - response
 *
 * @author yangjw
 */
@Data
@Schema(description = "管理后台 - 获取用户信息 Request VO")
public class UserInfoRespVO {

    /**
     * 用户主键ID
     */
    @Schema(description = "id", example = "1")
    private Long userId;

    /**
     * 用户名
     */
    @Schema(description = "用户名", example = "easy-share")
    private String username;


    /**
     * 昵称
     */
    @Schema(description = "昵称", example = "easy-share")
    private String nickname;

    /**
     * 头像URL
     */
    @Schema(description = "头像URL", example = "https://www.example.cn/xx.png")
    private String avatar;

    /**
     * 手机号
     */
    @Schema(description = "手机号", example = "15601691300")
    private String phone;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱", example = "yangjw@example.cn")
    private String email;

    /**
     * 性别：0未知 1男 2女
     */
    @Schema(description = "性别", example = "0")
    private Integer gender;

    /**
     * 生日
     */
    @Schema(description = "生日", example = "2022-01-01")
    private LocalDate birthday;

    /**
     * 个性签名
     */
    @Schema(description = "个性签名", example = "hello world")
    private String signature;

    /**
     * 学号/工号
     */
    @Schema(description = "工号", example = "202200000000")
    private String memberNo;

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
     * 学院
     */
    @Schema(description = "学院", example = "计算机科学与技术")
    private String college;


    /**
     * 用户角色：1学生 2学校负责人 3超级管理员
     */
    @Schema(description = "用户类型", example = "1")
    private String role;

    /**
     * 状态：1正常 0禁用
     */
    @Schema(description = "状态", example = "1")
    private Integer status;

    /**
     * 认证状态：0未认证 1认证中 2已认证 3驳回
     */
    @Schema(description = "认证状态", example = "0")
    private Integer verifyStatus;

}
