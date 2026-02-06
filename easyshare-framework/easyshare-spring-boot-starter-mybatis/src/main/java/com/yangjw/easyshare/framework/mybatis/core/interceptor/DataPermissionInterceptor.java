package com.yangjw.easyshare.framework.mybatis.core.interceptor;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.yangjw.easyshare.framework.common.enums.UserRoleEnum;
import com.yangjw.easyshare.framework.common.datapermission.DataPermissionContext;
import com.yangjw.easyshare.framework.common.datapermission.DataPermission;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;

public class DataPermissionInterceptor implements InnerInterceptor {

    /**
     * 查询前
     *
     * @param executor      执行器
     * @param ms            映射
     * @param parameter     参数
     * @param rowBounds     行数
     * @param resultHandler 结果处理器
     * @param boundSql
     * @throws SQLException
     */
    @Override
    public void beforeQuery(Executor executor,
                            MappedStatement ms,
                            Object parameter,
                            RowBounds rowBounds,
                            ResultHandler resultHandler,
                            BoundSql boundSql
    ) throws SQLException {
        // 获取数据权限
        DataPermission permission = DataPermissionContext.get();
        if (permission == null) {
            return;
        }
        // 构建 SQL
        String originalSql = boundSql.getSql();
        String permissionSql = buildPermissionSql(originalSql, permission);

        PluginUtils.mpBoundSql(boundSql).sql(permissionSql);
    }

    private String buildPermissionSql(String sql, DataPermission p) {
        // 超级管理员不加限制
        if (UserRoleEnum.SUPER_ADMIN.getRoleKey().equals(p.getRole())) {
            return sql;
        } else {
            // 学校管理员：school_id
            return sql + " AND school_id = " + p.getSchoolId();
        }
    }
}
