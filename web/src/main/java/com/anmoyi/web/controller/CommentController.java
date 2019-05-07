package com.anmoyi.web.controller;

import com.alibaba.fastjson.JSON;
import com.anmoyi.common.*;
import com.anmoyi.common.exception.TokenException;
import com.anmoyi.model.po.User;
import com.anmoyi.service.CommentService;
import com.anmoyi.service.UserService;
import com.anmoyi.service.vo.CommentVO;
import com.anmoyi.web.ao.CommentAO;
import com.anmoyi.web.ao.CommentListAO;
import com.anmoyi.web.ao.LoginAO;
import com.anmoyi.web.decrypt.XcxPhone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class CommentController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);


    @Autowired
    private UserService userService;


    @Autowired
    private CommentService commentService;

    /**
     * 评论
     * @param requestString
     * @return
     */
    @PostMapping(value = "/comment")
    public String comment(@RequestBody String requestString){
        logger.info("登陆");


        Packet packet = null;

        try {
            packet = JSON.parseObject(requestString, Packet.class);
        } catch (Exception e) {
            logger.error("登陆参数异常\n" + e);
            return responseToClient(AppError.APP_JSON_INVALID_ERROR);
        }


        CommentAO commentAO = null;
        try {
            //packet.getData() 为jsonobject
            commentAO = JSON.parseObject(packet.getData().toString(), CommentAO.class);
        } catch (Exception e) {
            logger.error("登陆参数异常",  e);
            return responseToClient(AppError.APP_ARGS_ERROR);
        }



        if (StringUtil.isNullOrBlank(commentAO.getContent())){
            logger.error("登陆参数异常");
            return responseToClient(AppError.APP_ARGS_ERROR);
        }



        String token = packet.getToken();
        String phone = null;
        try {
             phone = TokenUtil.getEmail(token);
        } catch (TokenException e) {
            logger.error("登陆参数异常",e);
            return responseToClient(AppError.APP_TOKEN_INVALID_ERROR);
        }

        User user = userService.getByPhone(phone);
        if (null == user){
            logger.error("登陆参数异常");
            return responseToClient(AppError.APP_TOKEN_INVALID_ERROR);
        }


        if (null != commentAO.getAvatarUrlArry()){
            commentService.addComment(user.getId(),commentAO.getContent(),commentAO.getAvatarUrlArry());
        }else {
            commentService.addComment(user.getId(),commentAO.getContent(),null);
        }


        return responseToClient(AppError.APP_OK);

    }




    /**
     * 获取评论列表
     * @param requestString
     * @return
     */
    @PostMapping(value = "/getCommentList")
    public String getCommentList(@RequestBody String requestString){
        logger.info("获取评论列表");

        Packet packet = null;

        try {
            packet = JSON.parseObject(requestString, Packet.class);
        } catch (Exception e) {
            logger.error(" 获取评论列表参数异常\n" + e);
            return responseToClient(AppError.APP_JSON_INVALID_ERROR);
        }


        CommentListAO commentListAO = null;
        try {
            //packet.getData() 为jsonobject
            commentListAO = JSON.parseObject(packet.getData().toString(), CommentListAO.class);
        } catch (Exception e) {
            logger.error(" 获取评论列表参数异常",  e);
            return responseToClient(AppError.APP_ARGS_ERROR);
        }



        if (commentListAO.getPageNum() < 1){
            logger.error(" 获取评论列表参数异常");
            return responseToClient(AppError.APP_ARGS_ERROR);
        }

        List<CommentVO> list = commentService.getCommentList(Const.AN_MO_YI_ID, commentListAO.getPageNum());

        return responseToClientWithData(AppError.APP_OK,list);

    }



}
