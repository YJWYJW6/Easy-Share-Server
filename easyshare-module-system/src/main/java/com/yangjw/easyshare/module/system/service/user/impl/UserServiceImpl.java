package com.yangjw.easyshare.module.system.service.user.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangjw.easyshare.framework.common.exception.ServiceException;
import com.yangjw.easyshare.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.yangjw.easyshare.framework.common.pojo.PageResult;
import com.yangjw.easyshare.framework.common.utils.AvatarUtils;
import com.yangjw.easyshare.framework.security.core.utils.SecurityUtils;
import com.yangjw.easyshare.module.system.config.propreties.EasyShareSysProperties;
import com.yangjw.easyshare.module.system.controller.admin.user.vo.*;
import com.yangjw.easyshare.module.system.convert.user.UserConvert;
import com.yangjw.easyshare.module.system.convert.user.UserVOConvert;
import com.yangjw.easyshare.module.system.dal.dataobject.user.UserDO;
import com.yangjw.easyshare.module.system.dal.mysql.user.UserMapper;
import com.yangjw.easyshare.module.system.enums.SysErrorCodeConstants;
import com.yangjw.easyshare.module.system.enums.user.UserGenderEnum;
import com.yangjw.easyshare.module.system.enums.user.UserRoleEnum;
import com.yangjw.easyshare.module.system.enums.user.UserStatusEnum;
import com.yangjw.easyshare.module.system.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    // config
    private final EasyShareSysProperties sysProperties;

    // mapper
    private final UserMapper userMapper;

    // convert
    private final UserConvert userConvert;

    private final UserVOConvert userVOConvert;

    // utils
    private final PasswordEncoder passwordEncoder;

    /**
     * 根据用户名查询用户
     */
    @Override
    public UserDO getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    /**
     * 校验密码是否匹配
     */
    @Override
    public Boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 记录 登录时间 和 登录IP
     */
    @Override
    public Integer recordLoginLog(Long userId, LocalDateTime loginTime, String ip) {
        return userMapper.updateLoginInfoById(userId, loginTime, ip);
    }

    /**
     * 获取登录用户信息
     */
    @Override
    public UserInfoRespVO getLoginUserInfo() {
        // 获取当前登录用户id
        Long loginUserId = SecurityUtils.getLoginUserId();
        if (ObjectUtil.isEmpty(loginUserId)) {
            throw new ServiceException(SysErrorCodeConstants.USER_NOT_EXISTS);
        }
        // 获取用户信息
        UserDO user = userMapper.selectById(loginUserId);
        if (ObjectUtil.isEmpty(user)) {
            throw new ServiceException(SysErrorCodeConstants.USER_NOT_EXISTS);
        }
        // 转换成返回结果
        return userConvert.convertUserInfo(user);
    }

    /**
     * 获取用户分页列表
     */
    @Override
    public PageResult<UserPageRespVO> getUserPage(UserPageReqVO reqVO) {
        // 1. 数据权限：学校管理员只能看自己学校
        Integer role = SecurityUtils.getLoginUserRole();
        if (ObjectUtil.equal(UserRoleEnum.SCHOOL_ADMIN.getCode(), role)) {
            reqVO.setSchoolId(SecurityUtils.getLoginSchoolId()); // 强制覆盖
        }

        // 2. 分页查询
        Page<UserDO> page = new Page<>(reqVO.getPageNo(), reqVO.getPageSize());

        // 3. 动态条件
        LambdaQueryWrapper<UserDO> qw = Wrappers.lambdaQuery();
        // keyword: username / nickname / memberNo
        if (StrUtil.isNotBlank(reqVO.getKeyword())) {
            String kw = reqVO.getKeyword().trim();
            qw.and(w -> w.like(UserDO::getUsername, kw)
                    .or().like(UserDO::getNickname, kw)
                    .or().like(UserDO::getMemberNo, kw));
        }
        // 精确条件
        qw.eq(reqVO.getRole() != null, UserDO::getRole, reqVO.getRole());
        qw.eq(reqVO.getStatus() != null, UserDO::getStatus, reqVO.getStatus());
        qw.eq(reqVO.getVerifyStatus() != null, UserDO::getVerifyStatus, reqVO.getVerifyStatus());
        // phone
        qw.eq(StrUtil.isNotBlank(reqVO.getPhone()), UserDO::getPhone, reqVO.getPhone());
        // schoolId（超管可筛选）
        qw.eq(reqVO.getSchoolId() != null, UserDO::getSchoolId, reqVO.getSchoolId());
        // createTime range
        if (reqVO.getCreateTimeStart() != null && reqVO.getCreateTimeEnd() != null) {
            qw.between(UserDO::getCreateTime, reqVO.getCreateTimeStart(), reqVO.getCreateTimeEnd());
        } else if (reqVO.getCreateTimeStart() != null) {
            qw.ge(UserDO::getCreateTime, reqVO.getCreateTimeStart());
        } else if (reqVO.getCreateTimeEnd() != null) {
            qw.le(UserDO::getCreateTime, reqVO.getCreateTimeEnd());
        }
        // lastLoginTime range
        if (reqVO.getLastLoginTimeStart() != null && reqVO.getLastLoginTimeEnd() != null) {
            qw.between(UserDO::getLastLoginTime, reqVO.getLastLoginTimeStart(), reqVO.getLastLoginTimeEnd());
        } else if (reqVO.getLastLoginTimeStart() != null) {
            qw.ge(UserDO::getLastLoginTime, reqVO.getLastLoginTimeStart());
        } else if (reqVO.getLastLoginTimeEnd() != null) {
            qw.le(UserDO::getLastLoginTime, reqVO.getLastLoginTimeEnd());
        }
        // 排序：最新创建在前
        qw.orderByDesc(UserDO::getCreateTime);

        // 4. 执行分页
        IPage<UserDO> result = userMapper.selectPage(page, qw);

        // 5. 转换
        return new PageResult<>(
                userConvert.convertUserPage(result.getRecords()),
                result.getTotal(),
                result.getCurrent()
        );
    }

    /**
     * 创建用户
     */
    @Override
    public Integer createUser(UserCreateReqVO reqVO) {
        // 1. 创建角色权限
        Integer operatorRole = SecurityUtils.getLoginUserRole();
        validateCreatePermission(operatorRole, reqVO.getRole());

        // 2. 学校 id 校验，如果是学校负责人只能创建自己学校的
        if (ObjectUtil.equal(UserRoleEnum.SCHOOL_ADMIN.getCode(), operatorRole)) {
            reqVO.setSchoolId(SecurityUtils.getLoginSchoolId());
        }

        // 3. 唯一性验证
        // username
        if (isUsernameExists(reqVO.getUsername())) {
            throw new ServiceException(SysErrorCodeConstants.USER_USERNAME_DUPLICATE);
        }
        // phone
        if (isPhoneExists(reqVO.getPhone())) {
            throw new ServiceException(SysErrorCodeConstants.USER_PHONE_DUPLICATE);
        }
        // email
        if (isEmailExists(reqVO.getEmail())) {
            throw new ServiceException(SysErrorCodeConstants.USER_EMAIL_DUPLICATE);
        }

        // 4. 封装数据，设置默认值
        UserDO user = userVOConvert.userCreateReqVOConvert(reqVO);
        if (user.getStatus() == null) { // 默认启用
            user.setStatus(UserStatusEnum.ENABLE.getCode());
        }
        if (user.getGender() == null) { // 默认性别未知
            user.setGender(UserGenderEnum.UNKNOWN.getCode());
        }
        if (user.getPassword() == null) { // 默认密码
            user.setPassword(passwordEncoder.encode(sysProperties.getDefaultPassword()));
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (StrUtil.isBlank(reqVO.getAvatar())) { // 默认头像
            user.setAvatar(AvatarUtils.diceBearAvatarByUsername(reqVO.getUsername()));
            // 或 randomDiceBearAvatar()
        }

        // 5. 插入
        return userMapper.insert(user);
    }

    /**
     * 更新用户
     */
    @Override
    public Integer updateUserProfile(UserUpdateProfileReqVO reqVO) {
        // 1. 判断权限
        validateUserAccessPermission(userMapper.selectById(reqVO.getId()));

        // 2. 唯一性验证
        // phone
        if (isPhoneExistsIgnoreUser(reqVO.getPhone(), reqVO.getId())) {
            throw new ServiceException(SysErrorCodeConstants.USER_PHONE_DUPLICATE);
        }
        // email
        if (isEmailExistsIgnoreUser(reqVO.getEmail(), reqVO.getId())) {
            throw new ServiceException(SysErrorCodeConstants.USER_EMAIL_DUPLICATE);
        }

        // 3. 封装数据
        UserDO user = userVOConvert.userUpdateProfileReqVOConvert(reqVO);
        return userMapper.updateById(user);
    }

    /**
     * 获取用户详情
     */
    @Override
    public UserDetailRespVO getUserDetail(Long id) {
        // 1. 判断权限
        validateUserAccessPermission(userMapper.selectById(id));

        // 2. 查询
        UserDO user = userMapper.selectById(id);

        // 3. 转换
        return userConvert.convertUserDetail(user);
    }

    /**
     * 校验用户名是否存在
     */
    private boolean isUsernameExists(String username) {
        if (StrUtil.isBlank(username)){
            return false;
        }
        return userMapper.selectByUsername(username) != null;
    }

    /**
     * 校验手机号是否存在
     */
    private boolean isPhoneExists(String phone) {
        if (StrUtil.isBlank(phone)){
            return false;
        }
        return userMapper.selectByPhone(phone) != null;
    }

    /**
     * 校验手机号是否存在（忽略指定用户）
     */
    private boolean isPhoneExistsIgnoreUser(String phone, Long ignoreUserId) {
        if (StrUtil.isBlank(phone)) {
            return false;
        }
        UserDO user = userMapper.selectByPhone(phone);
        if (user == null) {
            return false;
        }
        return !ObjectUtil.equal(user.getId(), ignoreUserId);
    }

    /**
     * 验证邮箱是否存在
     */
    private boolean isEmailExists(String email) {
        if (StrUtil.isBlank(email)){
            return false;
        }
        return userMapper.selectByEmail(email) != null;
    }

    /**
     * 校验邮箱是否存在（忽略指定用户）
     */
    private boolean isEmailExistsIgnoreUser(String email, Long ignoreUserId) {
        if (StrUtil.isBlank(email)) {
            return false;
        }
        UserDO user = userMapper.selectByPhone(email);
        if (user == null) {
            return false;
        }
        return !ObjectUtil.equal(user.getId(), ignoreUserId);
    }

    /**
     * 校验当前登录人是否有权限修改该用户资料
     */
    private void validateUserAccessPermission(UserDO targetUser) {
        // 1. 校验用户是否存在
        if (targetUser == null || targetUser.getId() == null) {
            throw new ServiceException(SysErrorCodeConstants.USER_NOT_EXISTS);
        }

        // 2. 验证当前操作人信息
        Long operatorId = SecurityUtils.getLoginUserId(); // 当前操作人
        Integer operatorRole = SecurityUtils.getLoginUserRole(); // 当前操作人角色
        Long operatorSchoolId = SecurityUtils.getLoginSchoolId(); // 当前操作人学校
        Long targetUserId = targetUser.getId();
        Integer targetUserRole = targetUser.getRole(); // 目标用户角色
        Long targetUserSchoolId = targetUser.getSchoolId(); // 目标用户学校
        if (operatorId == null || operatorRole == null) {
            throw new ServiceException(GlobalErrorCodeConstants.UNAUTHORIZED);
        }

        // 3. 判断修改人是否有权限
        // 3.1 目标是自己
        if (ObjectUtil.equal(operatorId, targetUserId)) return;
        // 3.2 超管：允许修改（自己/学生/学校管理员）
        if (ObjectUtil.equal(operatorRole, UserRoleEnum.SUPER_ADMIN.getCode())) {
            // 学生管理员 或者 学生
            if (ObjectUtil.equal(targetUserRole, UserRoleEnum.STUDENT.getCode()) ||
                    ObjectUtil.equal(targetUserRole, UserRoleEnum.SCHOOL_ADMIN.getCode())
            ) return;
        }
        // 3.3 学校管理员
        if (ObjectUtil.equal(operatorRole, UserRoleEnum.SCHOOL_ADMIN.getCode())) {
            // 2) 只能修改本学校的学生
            boolean sameSchool = ObjectUtil.equal(operatorSchoolId, targetUserSchoolId);
            boolean targetIsStudent = ObjectUtil.equal(targetUser.getRole(), UserRoleEnum.STUDENT.getCode());
            if (sameSchool && targetIsStudent) {
                return;
            }
        }

        // 4. 兜底：未知角色
        throw new ServiceException(SysErrorCodeConstants.USER_NO_PERMISSION_ACCESS);
    }

    /**
     * 校验当前登录人是否有权限创建该用户
     */
    private void validateCreatePermission(Integer operatorRole, Integer targetRole) {
        // 1. 验证当前登录人身份
        if (operatorRole == null) {
            throw new ServiceException(GlobalErrorCodeConstants.UNAUTHORIZED);
        }

        // 2. 校验目标身份是否合法
        if (targetRole == null || !UserRoleEnum.isExists(targetRole)) {
            throw new ServiceException(SysErrorCodeConstants.USER_CREATE_ROLE_INVALID);
        }

        // 3. 判断创建人是否有权限
        // 3.1 创建人是学生，禁止创建任何用户
        if (operatorRole.equals(UserRoleEnum.STUDENT.getCode())) {
            throw new ServiceException(SysErrorCodeConstants.USER_NO_PERMISSION_CREATE);
        }

        // 3.2 创建人是学校管理员，只能创建学生
        if (operatorRole.equals(UserRoleEnum.SCHOOL_ADMIN.getCode())) {
            if (!targetRole.equals(UserRoleEnum.STUDENT.getCode())) {
                // 创建者不是学生
                throw new ServiceException(SysErrorCodeConstants.USER_NO_PERMISSION_CREATE);
            }
            // 创建者是学生，允许创建
            return;
        }

        // 3.3 超级管理员：可以创建学生、学校管理员，但不允许创建超级管理员
        if (operatorRole.equals(UserRoleEnum.SUPER_ADMIN.getCode())) {
            if (targetRole.equals(UserRoleEnum.SUPER_ADMIN.getCode())) {
                throw new ServiceException(SysErrorCodeConstants.USER_NO_PERMISSION_CREATE);
            }
            return;
        }

        // 4. 兜底：未知角色
        throw new ServiceException(SysErrorCodeConstants.USER_NO_PERMISSION_CREATE);
    }

}
