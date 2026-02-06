package com.yangjw.easyshare.module.system.controller.admin.user;

import com.yangjw.easyshare.framework.common.pojo.CommonResult;
import com.yangjw.easyshare.framework.common.pojo.PageResult;
import com.yangjw.easyshare.framework.common.pojo.RowResult;
import com.yangjw.easyshare.module.system.controller.admin.user.vo.*;
import com.yangjw.easyshare.module.system.enums.user.UserStatusEnum;
import com.yangjw.easyshare.module.system.service.user.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "管理后台 - 用户")
@RestController
@RequestMapping("/admin/system/user")
@Validated
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    /**
     * 获取登录用户的信息
     */
    @PostMapping("/info")
    @Operation(summary = "获取登录用户的信息")
    public CommonResult<UserInfoRespVO> info() {
        return CommonResult.success(userService.getLoginUserInfo());
    }

    /**
     * 用户分页列表
     */
    @PostMapping("/page")
    @Operation(summary = "用户分页列表")
    public CommonResult<PageResult<UserPageRespVO>> getUserPage(@Validated @RequestBody UserPageReqVO reqVO) {
        return CommonResult.success(userService.getUserPage(reqVO));
    }

    /**
     * 创建用户
     */
    @PostMapping("/create")
    @Operation(summary = "创建用户")
    public CommonResult<RowResult> createUser(@Validated @RequestBody UserCreateReqVO reqVO) {
        return CommonResult.rows(userService.createUser(reqVO));
    }

    /**
     * 启用用户
     */
    @PutMapping("/enable/{id}")
    @Operation(summary = "启用用户")
    public CommonResult<RowResult> enableUser(@PathVariable("id") Long id) {
        return CommonResult.rows(userService.updateUserStatus(id, UserStatusEnum.ENABLE));
    }

    /**
     * 禁用用户
     */
    @PutMapping("/disable/{id}")
    @Operation(summary = "禁用用户")
    public CommonResult<RowResult> disableUser(@PathVariable("id") Long id) {
        return CommonResult.rows(userService.updateUserStatus(id, UserStatusEnum.DISABLE));
    }

    /**
     * 更新用户
     */
    @PostMapping("/updateProfile")
    @Operation(summary = "更新用户")
    public CommonResult<RowResult> updateUserProfile(@Validated @RequestBody UserUpdateProfileReqVO reqVO) {
        return CommonResult.rows(userService.updateUserProfile(reqVO));
    }

    /**
     * 获取用户信息
     */
    @PostMapping("/detail/{id}")
    @Operation(summary = "获取用户信息")
    public CommonResult<UserDetailRespVO> getUserInfo(@PathVariable("id") Long id) {
        return CommonResult.success(userService.getUserDetail(id));
    }
}
