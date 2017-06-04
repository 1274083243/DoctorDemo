package com.ike.commonutils.net.retrofitnetutils.model;

/**
 * @Description: 封装的通用服务器返回对象，可自行定义
 * @author:ike
 * @date: 2016-12-30 16:43
 */
public class ApiResult<T> {
    public int code;
    public String message;
    public T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "code=" + code +
                ", msg='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
