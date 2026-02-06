package com.yangjw.easyshare.framework.mybatis.core.utils;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.schema.Column;

public class MyBatisUtils {

    private MyBatisUtils() {
    }

    /**
     * 构建 Column 对象
     *
     * @param tableName  表名
     * @param tableAlias 别名
     * @param column     字段名
     * @return Column 对象
     */
    public static Column buildColumn(String tableName, Alias tableAlias, String column) {
        if (tableAlias != null) {
            tableName = tableAlias.getName();
        }
        return new Column(tableName + StringPool.DOT + column);
    }
}
