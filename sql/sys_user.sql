-- 系统用户表
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '用户主键ID',

    `username`        VARCHAR(32)  NOT NULL COMMENT '登录账号（唯一）',
    `password`        VARCHAR(255) NOT NULL COMMENT '密码（BCrypt等加密）',
    `nickname`        VARCHAR(32)  NOT NULL COMMENT '昵称',
    `avatar`          VARCHAR(255)          DEFAULT NULL COMMENT '头像URL',

    `phone`           VARCHAR(20)           DEFAULT NULL COMMENT '手机号',
    `email`           VARCHAR(64)           DEFAULT NULL COMMENT '邮箱',

    `gender`          TINYINT      NOT NULL DEFAULT 0 COMMENT '性别：0未知 1男 2女',
    `birthday`        DATE                  DEFAULT NULL COMMENT '生日',
    `signature`       VARCHAR(200)          DEFAULT NULL COMMENT '个性签名',

    `student_no`      VARCHAR(32)           DEFAULT NULL COMMENT '学号',
    `real_name`       VARCHAR(32)           DEFAULT NULL COMMENT '真实姓名',

    `school_id`       BIGINT                DEFAULT NULL COMMENT '学校ID（关联学校表）',
    `college`         VARCHAR(64)           DEFAULT NULL COMMENT '学院',
    `major`           VARCHAR(64)           DEFAULT NULL COMMENT '专业',
    `grade`           VARCHAR(16)           DEFAULT NULL COMMENT '年级（如2022）',
    `dormitory`       VARCHAR(64)           DEFAULT NULL COMMENT '宿舍/校区信息',

    `user_type`       TINYINT      NOT NULL DEFAULT 1 COMMENT '用户类型：1学生 2教师 3管理员',
    `status`          TINYINT      NOT NULL DEFAULT 1 COMMENT '状态：1正常 0禁用',
    `verify_status`   TINYINT      NOT NULL DEFAULT 0 COMMENT '认证状态：0未认证 1认证中 2已认证 3驳回',
    `credit_score`    INT          NOT NULL DEFAULT 100 COMMENT '信誉分（基础字段）',

    `remark`          VARCHAR(255)          DEFAULT NULL COMMENT '管理端备注',
    `last_login_time` DATETIME              DEFAULT NULL COMMENT '最近登录时间',
    `last_login_ip`   VARCHAR(45)           DEFAULT NULL COMMENT '最近登录IP',

    -- ===== 审计字段 =====
    `create_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `creator`         VARCHAR(64)           DEFAULT NULL COMMENT '创建者（UserId 字符串）',
    `updater`         VARCHAR(64)           DEFAULT NULL COMMENT '更新者（UserId 字符串）',
    -- ===== 逻辑删除 =====
    `deleted`         TINYINT(1)   NOT NULL DEFAULT 0 COMMENT '是否删除：0否 1是',

    PRIMARY KEY (`id`),

    UNIQUE KEY `uk_username` (`username`),
    UNIQUE KEY `uk_phone` (`phone`),
    UNIQUE KEY `uk_email` (`email`),

    KEY `idx_student_no` (`student_no`),
    KEY `idx_school_id` (`school_id`),
    KEY `idx_user_type` (`user_type`),
    KEY `idx_status` (`status`),
    KEY `idx_verify_status` (`verify_status`),
    KEY `idx_create_time` (`create_time`),
    KEY `idx_deleted` (`deleted`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='用户基础信息表';

INSERT INTO `sys_user` (`username`, `password`, `nickname`, `avatar`, `phone`,
                        `email`, `gender`, `birthday`, `signature`, `student_no`,
                        `real_name`, `school_id`, `college`, `major`, `grade`,
                        `dormitory`, `user_type`, `status`, `verify_status`, `credit_score`,
                        `remark`, `last_login_time`, `last_login_ip`, `create_time`,
                        `update_time`, `creator`, `updater`, `deleted`)
VALUES ('admin', '123456', '超级管理员', NULL, NULL,
        NULL, 0, NULL, NULL, NULL, '管理员', NULL,
        NULL, NULL, NULL, NULL, 3, 1, 2, 100, '系统初始化管理员',
        NULL, NULL, NOW(), NOW(), 'SYSTEM', 'SYSTEM', 0);