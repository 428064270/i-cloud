package com.icloud.common.utils.http;

/**
 * @author 42806
 */
public class HttpResponse<T> {

    // 响应业务状态
    private Integer code;

    // 响应消息
    private String msg;

    // 响应中的数据
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public HttpResponse(Integer status, String msg, T data) {
        this.code = status;
        this.msg = msg;
        this.data = data;
    }

    public HttpResponse(T data) {
        this.code = 200;
        this.msg = "success";
        this.data = data;
    }

    public HttpResponse() {
        this.code = 500;
        this.msg = "error";
        this.data = null;
    }

    public HttpResponse(String msg) {
        this.code = 500;
        this.msg = msg;
        this.data = null;
    }

    public static <T> HttpResponse<T> success(T data) {
        return new HttpResponse<T>(data);
    }

    public static HttpResponse success() {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setCode(200);
        httpResponse.setMsg("请求成功");
        httpResponse.setData(null);
        return httpResponse;
    }

    public static <T> HttpResponse<T> error(T data) {
        return new HttpResponse<T>(500, "error", data);
    }

    public static <T> HttpResponse<T> error(String msg) {
        return new HttpResponse<T>(msg);
    }

    public static <T> HttpResponse<T> error(Integer status, String msg) {
        return new HttpResponse<T>(status, msg, null);
    }


    @Override
    public String toString() {
        return "ResultUtil [status=" + code + ", msg=" + msg + ", data=" + data + "]";
    }
}
