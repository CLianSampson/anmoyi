package com.anmoyi.web.controller;

import com.alibaba.fastjson.JSON;
import com.anmoyi.common.*;
import com.anmoyi.common.exception.TokenException;
import com.anmoyi.model.po.User;
import com.anmoyi.service.UserService;
import com.anmoyi.web.ao.LoginAO;
import com.anmoyi.web.decrypt.XcxPhone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserService userService;


    /**
     * 获取用户信息
     * @param requestString
     * @return
     */
    @PostMapping(value = "/userInfo")
    public String userInfo(@RequestBody String requestString){
        logger.info("获取用户信息");


        Packet packet = null;

        try {
            packet = JSON.parseObject(requestString, Packet.class);
        } catch (Exception e) {
            logger.error("绑定锁参数异常\n" + e);
            return responseToClient(AppError.APP_JSON_INVALID_ERROR);
        }



        String token = packet.getToken();
        String email = null;
        try {
            email = TokenUtil.getEmail(token);
        } catch (TokenException e) {
            logger.error("获取用户信息参数错误");
            return responseToClient(AppError.APP_ARGS_ERROR);
        }


        User user = userService.getByPhone(email);

        //改变一下

        logger.info("获取用户信息完成");

        return responseToClientWithData(AppError.APP_OK,user);

    }



    /**
     * 登陆
     * @param requestString
     * @return
     */
    @PostMapping(value = "/login")
    public String login(@RequestBody String requestString){
        logger.info("登陆");


        Packet packet = null;

        try {
            packet = JSON.parseObject(requestString, Packet.class);
        } catch (Exception e) {
            logger.error("登陆参数异常\n" + e);
            return responseToClient(AppError.APP_JSON_INVALID_ERROR);
        }


        LoginAO loginAO = null;
        try {
            //packet.getData() 为jsonobject
            loginAO = JSON.parseObject(packet.getData().toString(), LoginAO.class);
        } catch (Exception e) {
            logger.error("登陆参数异常",  e);
            return responseToClient(AppError.APP_ARGS_ERROR);
        }



        if (StringUtil.isNullOrBlank(loginAO.getEncryptedData())){
            logger.error("登陆参数异常");
            return responseToClient(AppError.APP_ARGS_ERROR);
        }


        if (StringUtil.isNullOrBlank(loginAO.getIv())){
            logger.error("登陆参数异常");
            return responseToClient(AppError.APP_ARGS_ERROR);
        }

        if (StringUtil.isNullOrBlank(loginAO.getSessionKey())){
            logger.error("登陆参数异常");
            return responseToClient(AppError.APP_ARGS_ERROR);
        }


        if (loginAO.getSex()!=Const.SEX_MAN && loginAO.getSex()!=Const.SEX_WOMAN){
            logger.error("登陆参数异常");
            return responseToClient(AppError.APP_ARGS_ERROR);
        }


        String phone = null;
        try {
            phone = XcxPhone.decrypt(loginAO.getSessionKey(),loginAO.getIv(),loginAO.getEncryptedData());
        } catch (Exception e) {
            logger.error("登陆参数错误");
            return responseToClient(AppError.APP_ARGS_ERROR);
        }


        User user = new User();
        user.setAvatarUrl(loginAO.getAvatarUrl());
        user.setBirthDay(loginAO.getBirthDay());
        user.setNickName(loginAO.getNickName());
        user.setSex(loginAO.getSex());

        user.setPhone(phone);



        User returnUser = userService.getByPhone(phone);

        //改变一下

        logger.info("登陆完成");

        return responseToClientWithData(AppError.APP_OK,returnUser);

    }


}
