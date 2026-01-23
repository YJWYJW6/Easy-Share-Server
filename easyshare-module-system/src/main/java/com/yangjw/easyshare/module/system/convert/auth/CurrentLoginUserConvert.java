package com.yangjw.easyshare.module.system.convert.auth;

import com.yangjw.easyshare.framework.security.core.pojo.CurrentLoginUser;
import com.yangjw.easyshare.module.system.dal.dataobject.user.UserDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CurrentLoginUserConvert {

    /**
     * UserDO -> CurrentLoginUser
     */
    @Mapping(source = "id", target = "userId")
    CurrentLoginUser convert(UserDO user);
}
