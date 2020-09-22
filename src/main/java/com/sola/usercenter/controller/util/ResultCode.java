package com.sola.usercenter.controller.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 返回信息枚举
 */
@NoArgsConstructor
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(200, "成功"), FAIL(100, "失败");

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
