package com.anmoyi.common;

import com.anmoyi.common.exception.TokenException;

import java.util.UUID;

import static com.anmoyi.common.exception.TokenException.TOKEN_ERROR;

//import static com.ticket.common.ExceptionConst.TOKEN_ERROR;

/**
 * @author chen lian
 * @date 18/4/22 下午4:30
 */
public class TokenUtil {

    public static String getToken(String email){

        String random = UUID.randomUUID().toString();

        return Base64.encode(email + Const.SEPREATE_UNDERLINE + random);
    }


    public static String getEmail(String token) throws TokenException {

        byte[] decodeByte = Base64.decode(token);
        String decodeString = new String(decodeByte);

        String[] arry = decodeString.split(Const.SEPREATE_UNDERLINE);
        if (arry.length<2 || arry==null) {
            throw new TokenException(TOKEN_ERROR);
        }else {
            return arry[0];
        }
    }

    public static void main(String[] args) throws TokenException {

        String token = TokenUtil.getToken("18771098004");
        System.out.println(token);

        String string = "sampson";
        String phone = TokenUtil.getEmail(token);
        System.out.println(phone);
    }

}
