package com.anmoyi.web;

/**
 * @author chen lian
 * @date 18/4/22 下午5:16
 */
public class UrlConfig {

    public static String[] urlAyyy = {
            "/getAcessionKey",
            "/login",
            "/getCommentList",
            "/tokenCheck"
    };

    public static void main(String[] args) {
        for (String str: urlAyyy) {
            System.out.println(str);

        }
    }

}
