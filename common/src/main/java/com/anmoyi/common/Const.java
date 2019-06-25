package com.anmoyi.common;

/**
 * @author chen lian
 * @date 18/4/22 下午4:37
 */
public class Const {

    public static final String BASE64_RANDOM_COMPANYID = "MTIzOjQ1Njo3ODk";
    public static final String BASE32_TOKEN_KEY = "@B#32&*ABab";

    public static final String SEPREATE_UNDERLINE = "_";

    public static final int USER_LOGIN_INVALID_TIME = 15 * 24 * 60 * 60;// 单位，分钟,用户长时间未登陆时间   15天

    public static final int SMS_INVALID_TIME = 10;// 单位，分钟,用户长时间未登陆时间   10分钟



    public static final int PAGE_SIZE = 10;// 默认一页大小



    public static final String UPLOAD_FILE_PATH = "/anmoyi/uploadImage";


    public static final int SEX_MAN = 1;  //不是管理员
    public static final int SEX_WOMAN = 2;  //是管理员

    public static final int AN_MO_YI_ID = 1;  //按摩仪id


    public static final String APP_ID = "wxf7921050c476286d";
    public static final String APP_SECRET = "1b43b589d4204649e7473509fd650da4";


    //是否第一次登陆
    public static final boolean FIRST_LOGIN_FLAG_TRUE = true;   //是第一次登陆
    public static final boolean FIRST_LOGIN_FLAG_FALSE = false;  //不是第一次登陆





}
