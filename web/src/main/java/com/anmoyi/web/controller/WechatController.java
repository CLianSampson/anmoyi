package com.anmoyi.web.controller;


import com.alibaba.fastjson.JSON;
import com.anmoyi.common.AppError;
import com.anmoyi.common.Packet;
import com.anmoyi.common.StringUtil;
import com.anmoyi.model.po.User;
import com.anmoyi.service.UserService;
import com.anmoyi.web.ao.WXSessionKeyAO;
import com.anmoyi.web.dto.SessionKeyDTO;
import com.anmoyi.web.wx.WXUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WechatController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserService userService;



    /**
     * 获取微信 accession_key  ， 用来获取微信手机号
     * @param requestString
     * @return
     */
    @PostMapping(value = "/getAcessionKey")
    public String login(@RequestBody String requestString){
        logger.info("获取微信 accession_key 参数是:");

        logger.info(requestString);

        Packet packet = null;

        try {
            packet = JSON.parseObject(requestString, Packet.class);
        } catch (Exception e) {
            logger.error("获取微信 accession_key参数异常\n" + e);
            return responseToClient(AppError.APP_JSON_INVALID_ERROR);
        }


        WXSessionKeyAO wxSessionKeyAO = null;
        try {
            //packet.getData() 为jsonobject
            wxSessionKeyAO = JSON.parseObject(packet.getData().toString(), WXSessionKeyAO.class);
        } catch (Exception e) {
            logger.error("获取微信 accession_key参数异常",  e);
            return responseToClient(AppError.APP_ARGS_ERROR);
        }



        if (StringUtil.isNullOrBlank(wxSessionKeyAO.getLoginCode())){
            logger.error("获取微信 accession_key参数异常");
            return responseToClient(AppError.APP_ARGS_ERROR);
        }




        String  sessionKey = WXUtil.getOpenid(wxSessionKeyAO.getLoginCode());
        if (StringUtil.isNullOrBlank(sessionKey)){
            logger.error("获取微信 accession_key参数异常");
            return responseToClient(AppError.APP_ARGS_ERROR);
        }


        SessionKeyDTO sessionKeyDTO = new SessionKeyDTO();
        sessionKeyDTO.setSessionKey(sessionKey);


        //改变一下

        logger.info("获取微信 accession_key完成");

        return responseToClientWithData(AppError.APP_OK,sessionKeyDTO);

    }


}
