package com.anmoyi.web.decrypt;

import com.anmoyi.common.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;

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

        byte[] encData = Base64Util.decode(encDataStr);
        byte[] iv =Base64Util.decode(ivStr);
        byte[] key = Base64Util.decode(keyStr);

        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        return new String(cipher.doFinal(encData),"UTF-8");
    }





    public static void main(String[] args) throws Exception {
        String  encrypData = "dhxK6mXwVPUabiK9i78OP4bmjg1YEYlQNIimEm5WEU6PxB/nexMk+CeBgsH9FFI2v9ikDAjGShPjZJj4xsRkbOH4otOHB3GumTRzh9ZYxXlQyrOnyL2//Yr2AL/KYW47n3JxqeeiVP9y/5JDPcY5HAM4/XDV3SzROI730aItFdHmDhc0/CyDLH1WDzv8y24J9Z/eyv+GzP9rTpdKjN4DA==";
        String ivData =  "ZwRBTP8AtevYJtbLLh9Lg==";
        String sessionKey =  "3LL836kTRzaJ5llVmRN6w==";
        System.out.println(decrypt(sessionKey,ivData,encrypData));
    }



}
