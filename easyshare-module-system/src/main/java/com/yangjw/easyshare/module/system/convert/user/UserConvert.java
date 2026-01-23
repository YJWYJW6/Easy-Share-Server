package com.yangjw.easyshare.module.system.convert.user;

import com.yangjw.easyshare.module.system.controller.admin.user.vo.UserDetailRespVO;
import com.yangjw.easyshare.module.system.controller.admin.user.vo.UserInfoRespVO;
import com.yangjw.easyshare.module.system.controller.admin.user.vo.UserPageRespVO;
import com.yangjw.easyshare.module.system.dal.dataobject.user.UserDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * 用户相关对象转换器
 */
@Mapper(componentModel = "spring")
public interface UserConvert {

    /**
     * UserDO -> UserInfoRespVO
     */
    @Mapping(source = "id", target = "userId")
    UserInfoRespVO convertUserInfo(UserDO userDO);

    /**
     * UserDO -> UserPageRespVO
     */
    List<UserPageRespVO> convertUserPage(List<UserDO> list);

    /**
     * UserDO -> UserDetailRespVO
     */
    UserDetailRespVO convertUserDetail(UserDO userDO);

}
