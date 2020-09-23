package com.sola.usercenter.controller.util;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable, IResult {
    private static final long serialVersionUID = 1L;

    private String message;

    private Integer code;

    private T data;

    public Result(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    public Result(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public static Result ok() {
        return new Result(ResultCode.SUCCESS);
    }

    public static<T> Result ok(T data) {
        return new Result(ResultCode.SUCCESS, data);
    }

    public static Result error() {
        return new Result(ResultCode.FAIL);
    }

    public static Result error(String message) {
        Result result = new Result();
        result.code = ResultCode.FAIL.getCode();
        result.message = message;
        return result;
    }
}
