package com.yangjw.easyshare.module.system.convert.user;

import com.yangjw.easyshare.module.system.controller.admin.user.vo.UserCreateReqVO;
import com.yangjw.easyshare.module.system.controller.admin.user.vo.UserUpdateProfileReqVO;
import com.yangjw.easyshare.module.system.dal.dataobject.user.UserDO;
import org.mapstruct.Mapper;

/**
 * 用户相关VO对象转换器
 *
 * @author yangjw
 */
@Mapper(componentModel = "spring")
public interface UserVOConvert {

    UserDO userCreateReqVOConvert(UserCreateReqVO reqVO);

    UserDO userUpdateProfileReqVOConvert(UserUpdateProfileReqVO reqVO);
}
