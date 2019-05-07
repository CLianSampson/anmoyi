package com.anmoyi.common.exception;

/**
 * @author chen lian
 * @date 18/4/22 下午4:45
 */
public class TokenException extends Exception {

    //token 不合法
    public static String TOKEN_ERROR = "token is uncorrect";


    public TokenException(String message){
        super(message);
    }

}
