package com.anmoyi.web.decrypt;




import com.alibaba.fastjson.JSON;
import com.anmoyi.common.Base64;
import com.anmoyi.web.ao.LoginAO;

import java.security.spec.AlgorithmParameterSpec;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/*
 * 1、先调用微信  // wx.login({   
    //                success:function(res){
    //                             console.log('loginCode:', res.code)
    //                                      }
    //          });


    loginCode: 001wpi9M0QwiOa200I8M0Xnv9M0wpi9i 只能使用一次


    2、后台调用getopenid  url生成 seesionkey

    wx pay return xml:{"session_key":"kI+3ookOAYC9olOcGzYPmQ==","openid":"oNZE65Pu0XfTg2yFP-dFk51VJTks"}

    3、前端 调用 getphonenumber  生成 iv 和 ecryptdata

    4、后台解密

 */




//对称密钥
//
//        非对称密钥
//
//        散列
//
//        电子签名


public class XcxPhone {

    /**
     * 解密用户电话
     * @param keyStr sessionkey
     * @param ivStr  ivData
     * @param encDataStr 带解密数据
     * @return
     * @throws Exception
     * @author pangxianhe
     * @date 2018年10月22日
     */
    public static String decrypt(String keyStr, String ivStr, String encDataStr)throws Exception {

        byte[] encData = Base64.decode(encDataStr);
        byte[] iv =Base64.decode(ivStr);
        byte[] key = Base64.decode(keyStr);

        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);



        String jsonStr = new String(cipher.doFinal(encData),"UTF-8");

        Map<String, Object> map = JSON.parseObject(jsonStr, Map.class);

        String phone = (String) map.get("phoneNumber");


        return phone;
    }





    public static void main(String[] args) throws Exception {
//        String  encrypData = "rV1GFoT8RjCTUlEL93AL5np+WWrjt2lrQPAZQhIOGdM75l4i4HmQGEXIBcwfkIUUQNbwCvWKNA/99SWu5eWpSU7E5t/rGm/Qqt7BGEF3xtSvlXhbvRVscqLNRtnJTQLhJz4ZONk+gggi9jrLBFX01IShs8eoITiUdOMRc2J88Jvsz+Ek21tSoB7kkwOe8raYE3PnWYoQz7aiV210y6n1QA==";
//        String sessionKey =  "yNjJc5jQHZmDWFQwSJQYcQ==";
//
//        String ivData = "55CepML7rJMM8b21EV2O/A==";


        String  encrypData = "r3pqf4F5zJhACy2rIQcrHz1p1vZP3lKcgyhku5cHhC4Zc3jaPUGOXsPNEF0kYA7kR0OPQvo2tdQoySvAU5R0vCls8FmxG+DYbY5KdYxZ2/HXKCNmJymWRG8PiT3fbd97Mqt9SClE0GFx89z64NWhnF3A5Lu49y70cxmKx1q+WgkkmrcdLAuZNHQLRNvnWHGN0a47lEwc5EFg3pEW9cbmUUPe4t0frXHjDxX40KezMvQtMcvjreRfF2o9kK3TKUeSe0DPwd84sAtX/xhPHKUMsKRwo7hjOQmgetYLs+a5J4i41XWeoOv3ga4qFCw+5gbYkV1NbAsEJrXCpu9QZl/iX7QZrJHglXfKJz0hNANegRNt70i7chUjzoGevCSOXLQ+wiZaLc9fi5kXBL4/9wqRW2hPg1gE2X6eNvsIUh3WXc12bDkG6DfY9hVrwk8reYSUvncmc8MKFPKIakpP2NUQ0GuVusDmXexI49IqjM3iHho=";
        String  sessionKey =  "Wn0hv5fps/C+1vLI1pxn5Q==";
        String  ivData = "6dZ6H1PMbrdAn1WudInfSg==";

        String phone = decrypt(sessionKey,ivData,encrypData);



        System.out.println(phone);

    }




}
