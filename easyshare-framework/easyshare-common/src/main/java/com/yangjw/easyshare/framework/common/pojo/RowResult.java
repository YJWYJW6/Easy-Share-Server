package com.yangjw.easyshare.framework.common.pojo;

import lombok.Data;

@Data
public class RowResult {

    private Integer row;

    public static RowResult of(Integer row) {
        RowResult rowResult = new RowResult();
        rowResult.setRow(row);
        return rowResult;
    }
}
