package com.yangjw.easyshare.framework.common.pojo;

import com.yangjw.easyshare.framework.common.exception.ServiceException;
import com.yangjw.easyshare.framework.common.exception.enums.GlobalErrorCodeConstants;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通用返回
 *
 * @param <T> 数据泛型
 */
@Data
public class CommonResult<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * {@link GlobalErrorCodeConstants}
     * 状态码
     */
    private Integer code;

    /**
     * 提示，用户可阅读
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    // ==================== 成功返回 ====================
    public static <T> CommonResult<T> success() {
        return success(null);
    }

    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> result = new CommonResult<>();
        result.code = GlobalErrorCodeConstants.SUCCESS.getCode();
        result.msg = GlobalErrorCodeConstants.SUCCESS.getMsg();
        result.data = data;
        return result;
    }

    // ==================== 失败返回 ====================
    public static <T> CommonResult<T> error(String msg) {
        return error(GlobalErrorCodeConstants.ERROR.getCode(), msg);
    }

    public static <T> CommonResult<T> error(Integer code, String msg) {
        CommonResult<T> result = new CommonResult<>();
        result.code = code;
        result.msg = msg;
        result.data = null;
        return result;
    }

    // ==================== DB 影响行数校验 ====================
    public static CommonResult<RowResult> rows(Integer rows) {
        if (rows == null || rows <= 0) {
            throw new ServiceException(GlobalErrorCodeConstants.ERROR);
        }
        return CommonResult.success(RowResult.of(rows));
    }

}
