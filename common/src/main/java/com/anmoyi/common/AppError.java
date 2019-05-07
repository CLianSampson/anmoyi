package com.anmoyi.common;

/**
 * @author chen lian
 * @date 18/4/22 下午5:02
 */
public enum AppError {

    APP_OK(1, "ok"),

    APP_ERROR(-1,"系统异常"),

    APP_JSON_INVALID_ERROR(-2,"json格式错误"),

    APP_ARGS_ERROR(-3,"参数错误"),

    APP_URL_ERROR(-4,"url不合法"),

    APP_METHOD_ERROR(-5,"不支持get "),


    APP_TOKEN_IS_NULL_ERROR(-10,"token为空"),

    APP_TOKEN_INVALID_ERROR(-11,"token无效aaaaaaaaaaaaaa"),


    APP_EMAIL_EXIST_ERROR(-12,"邮箱已注册"),

    APP_EMAIL_NOT_EXIST_ERROR(-12,"邮箱不存在"),

    APP_PASSWORD_ERROR(-13,"密码错误"),



    EMPTY(0, "起到结束作用");

    private int code;

    private String message;


    AppError(int code, String message) {
        this.code = code;
        this.message = message;
    }

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


    @Override
    public String toString() {
        return "AppError{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
