package com.anmoyi.common.exception;

public class ArgsException extends Exception{

    public static String LOCK_USER_ARGS_ERROR = "lock user args error";

    public static String LOCK_USER_EMAIL_ARGS_ERROR = "add lock user email args error";

    public static String ADD_BLUE_THOOTH_ARGS_ERROR = "blue thooth id args error";


    public static String ADD_BLUE_THOOTH_EXIST_ARGS_ERROR = "blue thooth exist args error";


    public ArgsException(String message){
        super(message);
    }



}
