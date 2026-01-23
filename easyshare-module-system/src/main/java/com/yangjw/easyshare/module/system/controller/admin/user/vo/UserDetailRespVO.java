package com.yangjw.easyshare.module.system.controller.admin.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户详情VO - response
 *
 * @author yangjw
 */
@Data
@Schema(description = "管理后台 - 获取用户详情 Request VO")
public class UserDetailRespVO {

    /**
     * 用户主键ID
     */
    @Schema(description = "id", example = "1")
    private Long id;

    /**
     * 登录账号
     */
    @Schema(description = "用户名", example = "admin")
    private String username;

    /**
     * 昵称
     */
    @Schema(description = "昵称", example = "张三")
    private String nickname;

    /**
     * 头像URL
     */
    @Schema(description = "头像URL", example = "https://www.github.com/xx.png")
    private String avatar;

    /**
     * 手机号
     */
    @Schema(description = "手机号", example = "15601691300")
    private String phone;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱", example = "admoin@example.com")
    private String email;

    /**
     * 性别：0未知 1男 2女
     */
    @Schema(description = "性别：0未知 1男 2女", example = "1")
    private Integer gender;

    /**
     * 生日
     */
    @Schema(description = "生日", example = "2022-02-02")
    private LocalDate birthday;

    /**
     * 个性签名
     */
    @Schema(description = "个性签名", example = "Hello World")
    private String signature;

    /**
     * 学号/工号
     */
    @Schema(description = "学号/工号", example = "202100000000")
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
    @Schema(description = "学院", example = "计算机学院")
    private String college;

    /**
     * 专业
     */
    @Schema(description = "专业", example = "计算机科学与技术")
    private String major;

    /**
     * 年级（如2022）
     */
    @Schema(description = "年级", example = "2022")
    private String grade;

    /**
     * 宿舍/校区信息
     */
    @Schema(description = "宿舍/校区信息", example = "101")
    private String dormitory;

    /**
     * 用户角色
     */
    @Schema(description = "用户角色", example = "1")
    private Integer role;

    /**
     * 状态
     */
    @Schema(description = "状态", example = "1")
    private Integer status;

    /**
     * 认证状态
     */
    @Schema(description = "认证状态", example = "1")
    private Integer verifyStatus;

    /**
     * 信誉分
     */
    @Schema(description = "信誉分", example = "1")
    private Integer creditScore;

    /**
     * 管理端备注
     */
    @Schema(description = "管理端备注", example = "无")
    private String remark;

    /**
     * 最近登录时间
     */
    @Schema(description = "最近登录时间", example = "2022-02-02 00:00:00")
    private LocalDateTime lastLoginTime;

    /**
     * 最近登录IP
     */
    @Schema(description = "最近登录IP", example = "127.0.0.1")
    private String lastLoginIp;

}
