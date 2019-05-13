package com.anmoyi.web.controller;


import com.alibaba.fastjson.JSON;
import com.anmoyi.common.*;
import com.anmoyi.common.exception.TokenException;
import com.anmoyi.model.po.Suggestion;
import com.anmoyi.model.po.User;
import com.anmoyi.service.SuggestionService;
import com.anmoyi.service.UserService;
import com.anmoyi.service.vo.CommentVO;
import com.anmoyi.web.ao.CommentListAO;
import com.anmoyi.web.ao.SuggestionAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SuggestionController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(SuggestionController.class);


    @Autowired
    private SuggestionService suggestionService;


    @Autowired
    private UserService userService;


    @PostMapping(value = "/addSuggestion")
    public String addSuggestion(@RequestBody String requestString){
        logger.info("意见反馈列数是: ");
        logger.info(requestString);


        Packet packet = null;

        try {
            packet = JSON.parseObject(requestString, Packet.class);
        } catch (Exception e) {
            logger.error(" 意见反馈参数异常\n" + e);
            return responseToClient(AppError.APP_JSON_INVALID_ERROR);
        }


        SuggestionAO suggestionAO = null;
        try {
            //packet.getData() 为jsonobject
            suggestionAO = JSON.parseObject(packet.getData().toString(), SuggestionAO.class);
        } catch (Exception e) {
            logger.error("意见反馈参数异常",  e);
            return responseToClient(AppError.APP_ARGS_ERROR);
        }



        if (StringUtil.isNullOrBlank(suggestionAO.getContent())){
            logger.error("意见反馈参数异常");
            return responseToClient(AppError.APP_ARGS_ERROR);
        }




        String phone = null;
        try {
            phone = TokenUtil.getEmail(packet.getToken());
        } catch (TokenException e) {
            logger.error("意见反馈参数异常",e);
            return responseToClient(AppError.APP_TOKEN_INVALID_ERROR);
        }

        User user = userService.getByPhone(phone);
        if (null == user){
            logger.error("意见反馈参数异常");
            return responseToClient(AppError.APP_TOKEN_INVALID_ERROR);
        }

        this.token = user.getToken();


        Suggestion suggestion = new Suggestion();
        suggestion.setUserId(user.getId());
        suggestion.setContent(suggestionAO.getContent());


        suggestionService.addSuggestion(suggestion);

        logger.info("意见反馈完成");

        return responseToClient(AppError.APP_OK);

    }




}
