package com.anmoyi.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.anmoyi.common.AppError;
import com.anmoyi.common.Packet;
import com.anmoyi.common.TokenUtil;
import com.anmoyi.model.po.User;
import com.anmoyi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
public abstract class BaseController {
    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private UserService userService;

    protected String responseToClient(AppError appError){
        Packet rtvPacket = new Packet();
        rtvPacket.setCode(appError.getCode());
        //rtvPacket.setData(appError.getMessage());

        rtvPacket.setMessage(appError.getMessage());

//        PrintWriterUtil.writeResultToClient(response, JSON.toJSON(rtvPacket).toString());

        return  JSON.toJSON(rtvPacket).toString();
    }


    protected String responseToClientWithData(AppError appError,Object data){
        Packet rtvPacket = new Packet();
        rtvPacket.setCode(appError.getCode());
        rtvPacket.setData(data);

        rtvPacket.setMessage(AppError.APP_OK.getMessage());

//        PrintWriterUtil.writeResultToClient(response, JSON.toJSON(rtvPacket).toString());

        //SerializerFeature.WriteMapNullValue  显示null 的 key
        logger.info("返回的数据是: " + JSON.toJSONString(rtvPacket, SerializerFeature.WriteMapNullValue));

        return  JSON.toJSONString(rtvPacket, SerializerFeature.WriteMapNullValue).toString();
    }

    protected User getUser(Packet packet){
        String token = packet.getToken();
        String phone = null;
        try {
            phone = TokenUtil.getEmail(token);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("无效参数token，可能遭到攻击");
            responseToClient(AppError.APP_TOKEN_INVALID_ERROR);

            return null;
        }

        User user = userService.getByPhone(phone);
        if (user==null){
            logger.error("无效参数token，可能遭到攻击");
            responseToClient(AppError.APP_TOKEN_INVALID_ERROR);

            return null;
        }

        return user;
    }

}
