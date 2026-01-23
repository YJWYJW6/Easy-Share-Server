package com.yangjw.easyshare.module.system.controller.admin.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;


/**
 * 修改用户VO - request
 *
 * @author yangjw
 */
@Data
@Schema(description = "管理后台 - 修改用户 Request VO")

public class UserUpdateProfileReqVO {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID", example = "1")
    private Long id;

    /**
     * 昵称
     */
    @Size(max = 32)
    @Schema(description = "昵称", example = "tom")
    private String nickname;

    /**
     * 手机
     */
    @Pattern(regexp = "^1\\d{10}$", message = "手机号格式不正确")
    @Schema(description = "手机", example = "15601691300")
    private String phone;

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    @Schema(description = "邮箱", example = "yangjw@gmail.com")
    private String email;

    /**
     * 头像
     */
    @Schema(description = "头像", example = "https://www.github.com/xx.png")
    private String avatar;

    /**
     * 性别
     */
    @Schema(description = "性别", example = "1")
    private Integer gender;

    /**
     * 学号
     */
    @Schema(description = "学号", example = "202100000000")
    private String memberNo;

    /**
     * 真实姓名
     */
    @Schema(description = "真实姓名", example = "张三")
    private String realName;

    /**
     * 所属学院
     */
    @Schema(description = "所属学院", example = "计算机学院")
    private String college;

    /**
     * 专业
     */
    @Schema(description = "专业", example = "计算机科学与技术")
    private String major;

    /**
     * 年级
     */
    @Schema(description = "年级", example = "2020")
    private String grade;

    /**
     * 宿舍
     */
    @Schema(description = "宿舍", example = "101")
    private String dormitory;

    /**
     * 个性签名
     */
    @Schema(description = "个性签名", example = "Hello World")
    private String signature;

    /**
     * 备注
     */
    @Schema(description = "备注", example = "无")
    private String remark;
}

