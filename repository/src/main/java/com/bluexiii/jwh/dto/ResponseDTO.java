package com.bluexiii.jwh.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T> {

    String code;
    String msg;
    T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static ResponseDTO succ(Object data) {
        return responseModel(data, "0000", "成功");
    }

    public static ResponseDTO fail(String code, String msg) {
        return responseModel(null, code, msg);
    }

    public static ResponseDTO fail(String code, String msg, Object data) {
        return responseModel(data, code, msg);
    }

    public static ResponseDTO responseModel(Object data, String code, String msg) {
        ResponseDTO response = new ResponseDTO();
        response.setCode(code);
        response.setMsg(msg);
        response.setResult(data);
        return response;
    }

}
