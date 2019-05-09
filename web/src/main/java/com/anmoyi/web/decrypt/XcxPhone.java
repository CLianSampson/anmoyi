package com.anmoyi.web.decrypt;




import com.anmoyi.common.Base64;

import java.security.spec.AlgorithmParameterSpec;

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



        return new String(cipher.doFinal(encData),"UTF-8");
    }





    public static void main(String[] args) throws Exception {
        String  encrypData = "rV1GFoT8RjCTUlEL93AL5np+WWrjt2lrQPAZQhIOGdM75l4i4HmQGEXIBcwfkIUUQNbwCvWKNA/99SWu5eWpSU7E5t/rGm/Qqt7BGEF3xtSvlXhbvRVscqLNRtnJTQLhJz4ZONk+gggi9jrLBFX01IShs8eoITiUdOMRc2J88Jvsz+Ek21tSoB7kkwOe8raYE3PnWYoQz7aiV210y6n1QA==";
        String sessionKey =  "yNjJc5jQHZmDWFQwSJQYcQ==";

        String ivData = "55CepML7rJMM8b21EV2O/A==";

        System.out.println(decrypt(sessionKey,ivData,encrypData));

    }





}
