package com.yangjw.easyshare.framework.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResult<T> {
    private List<T> list;
    private Long total;
    private Long current;
}
