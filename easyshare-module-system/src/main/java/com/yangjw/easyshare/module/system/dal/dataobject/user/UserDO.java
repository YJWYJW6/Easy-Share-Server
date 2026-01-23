package com.yangjw.easyshare.module.system.dal.dataobject.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yangjw.easyshare.framework.mybatis.core.dataobject.BaseDO;
import com.yangjw.easyshare.module.system.enums.user.UserRoleEnum;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 用户基础信息表 DO
 * <p>
 * 对应表：sys_user
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class UserDO extends BaseDO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 登录账号（唯一）
     */
    private String username;

    /**
     * 加密后的密码
     * <p>
     * 因为目前使用 {@link BCryptPasswordEncoder} 加密器，所以无需自己处理 salt 盐
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别：0未知 1男 2女
     * <p>
     * {@link com.yangjw.easyshare.module.system.enums.user.UserGenderEnum}
     */
    private Integer gender;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 学号/工号
     */
    private String memberNo;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 学校ID（关联学校表）
     */
    private Long schoolId;

    /**
     * 学院
     */
    private String college;

    /**
     * 专业
     */
    private String major;

    /**
     * 年级（如2022）
     */
    private String grade;

    /**
     * 宿舍/校区信息
     */
    private String dormitory;

    /**
     * 用户角色：1学生 2学校负责人 3超级管理员
     * <p>
     * {@link UserRoleEnum}
     */
    private Integer role;

    /**
     * 状态：1正常 0禁用
     */
    private Integer status;

    /**
     * 认证状态：0未认证 1认证中 2已认证 3驳回
     * <p>
     * {@link com.yangjw.easyshare.module.system.enums.user.UserVerifyStatusEnum}
     */
    private Integer verifyStatus;

    /**
     * 信誉分
     */
    private Integer creditScore;

    /**
     * 管理端备注
     */
    private String remark;

    /**
     * 最近登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 最近登录IP
     */
    private String lastLoginIp;

}
