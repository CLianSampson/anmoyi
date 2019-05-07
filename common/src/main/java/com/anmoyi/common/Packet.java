package com.anmoyi.common;

public class Packet {

    private int code; //返回码

    private String token;

    //接收参数时是jsonobject
    private Object data;  //返回数据，json字符串


    private String message;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "Packet{" +
                "code=" + code +
                ", token='" + token + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }


}
