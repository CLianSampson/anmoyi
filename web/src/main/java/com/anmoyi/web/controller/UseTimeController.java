package com.anmoyi.web.controller;

import com.alibaba.fastjson.JSON;
import com.anmoyi.common.AppError;
import com.anmoyi.common.Const;
import com.anmoyi.common.Packet;
import com.anmoyi.common.TokenUtil;
import com.anmoyi.common.exception.TokenException;
import com.anmoyi.model.po.UseTime;
import com.anmoyi.model.po.User;
import com.anmoyi.service.UseTimeService;
import com.anmoyi.service.UserService;
import com.anmoyi.service.vo.UseTimeVO;
import com.anmoyi.web.ao.PeriodUseTimeAO;
import com.anmoyi.web.ao.UseTimeAO;
import com.anmoyi.web.ao.UseTimeListAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class UseTimeController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UseTimeController.class);


    @Autowired
    private UserService userService;


    @Autowired
    private UseTimeService useTimeService;

    /**
     * 上报使用时间
     * @param requestString
     * @return
     */
    @PostMapping(value = "/addUseTime")
    public String addUseTime(@RequestBody String requestString){
        logger.info("评论");


        Packet packet = null;

        try {
            packet = JSON.parseObject(requestString, Packet.class);
        } catch (Exception e) {
            logger.error("上报使用时间参数异常\n" + e);
            return responseToClient(AppError.APP_JSON_INVALID_ERROR);
        }


        UseTimeAO useTimeAO = null;
        try {
            //packet.getData() 为jsonobject
            useTimeAO = JSON.parseObject(packet.getData().toString(), UseTimeAO.class);
        } catch (Exception e) {
            logger.error("上报使用时间参数异常",  e);
            return responseToClient(AppError.APP_ARGS_ERROR);
        }



        if (null == useTimeAO.getTime()){
            logger.error("上报使用时间参数异常");
            return responseToClient(AppError.APP_ARGS_ERROR);
        }


        /**
         去掉 大于 9
        if (useTimeAO.getPointType() < 0 || useTimeAO.getPointType() > 9){
            logger.error("上报使用时间参数异常");
            return responseToClient(AppError.APP_ARGS_ERROR);
        }
         */

        if (useTimeAO.getPointType() < 0 ){
            logger.error("上报使用时间参数异常");
            return responseToClient(AppError.APP_ARGS_ERROR);
        }


        if (useTimeAO.getDuration() < 0 ){
            logger.error("上报使用时间参数异常");
            return responseToClient(AppError.APP_ARGS_ERROR);
        }

        String token = packet.getToken();
        String phone = null;
        try {
            phone = TokenUtil.getEmail(token);
        } catch (TokenException e) {
            logger.error("上报使用时间参数异常",e);
            return responseToClient(AppError.APP_TOKEN_INVALID_ERROR);
        }

        User user = userService.getByPhone(phone);
        if (null == user){
            logger.error("上报使用时间参数异常");
            return responseToClient(AppError.APP_TOKEN_INVALID_ERROR);
        }

        //设置返回token
        this.token = user.getToken();



        UseTime useTime = new UseTime();

        useTime.setUseTime(useTimeAO.getTime());
        useTime.setDuration(useTimeAO.getDuration());
        useTime.setUserId(user.getId());
        useTime.setPointType(useTimeAO.getPointType());
        useTime.setCreateTime(new Date());

        useTimeService.addUseTime(useTime);

        return responseToClient(AppError.APP_OK);

    }






    /**
     * 获取一段时间使用次数
     * @param requestString
     * @return
     */
    @PostMapping(value = "/getPeriodUseTime")
    public String getPeriodUseTime(@RequestBody String requestString){
        logger.info("获取一段时间使用次数次数");


        Packet packet = null;

        try {
            packet = JSON.parseObject(requestString, Packet.class);
        } catch (Exception e) {
            logger.error("获取一段时间使用次数参数异常\n" + e);
            return responseToClient(AppError.APP_JSON_INVALID_ERROR);
        }


        PeriodUseTimeAO periodUseTimeAO = null;
        try {
            //packet.getData() 为jsonobject
            periodUseTimeAO = JSON.parseObject(packet.getData().toString(), PeriodUseTimeAO.class);
        } catch (Exception e) {
            logger.error("获取一段时间使用次数参数异常",  e);
            return responseToClient(AppError.APP_ARGS_ERROR);
        }



        if (null == periodUseTimeAO.getStartTime()){
            logger.error("获取一段时间使用次数参数异常");
            return responseToClient(AppError.APP_ARGS_ERROR);
        }


        if (null == periodUseTimeAO.getEndTime()){
            logger.error("获取一段时间使用次数参数异常");
            return responseToClient(AppError.APP_ARGS_ERROR);
        }


        if (periodUseTimeAO.getPointType() < 0 ){
            logger.error("上报使用时间参数异常");
            return responseToClient(AppError.APP_ARGS_ERROR);
        }


        String token = packet.getToken();
        String phone = null;
        try {
            phone = TokenUtil.getEmail(token);
        } catch (TokenException e) {
            logger.error("获取一段时间使用次数参数异常",e);
            return responseToClient(AppError.APP_TOKEN_INVALID_ERROR);
        }

        User user = userService.getByPhone(phone);
        if (null == user){
            logger.error("获取一段时间使用次数参数异常");
            return responseToClient(AppError.APP_TOKEN_INVALID_ERROR);
        }

        //设置返回token
        this.token = user.getToken();



        List<UseTimeVO> returnList = useTimeService.getPeriodUseTimeList(user.getId(), periodUseTimeAO.getPointType() ,periodUseTimeAO.getStartTime(),periodUseTimeAO.getEndTime());

        return responseToClientWithData(AppError.APP_OK,returnList);

    }





    /**
     * 获取当天使用次数
     * @param requestString
     * @return
     */
    @PostMapping(value = "/getUseTimeList")
    public String getUseTimeList(@RequestBody String requestString){
        logger.info("获取当天使用次数");


        Packet packet = null;

        try {
            packet = JSON.parseObject(requestString, Packet.class);
        } catch (Exception e) {
            logger.error("获取当天使用次数参数异常\n" + e);
            return responseToClient(AppError.APP_JSON_INVALID_ERROR);
        }


        UseTimeListAO useTimeListAO = null;
        try {
            //packet.getData() 为jsonobject
            useTimeListAO = JSON.parseObject(packet.getData().toString(), UseTimeListAO.class);
        } catch (Exception e) {
            logger.error("获取当天使用次数参数异常",  e);
            return responseToClient(AppError.APP_ARGS_ERROR);
        }



        if (null == useTimeListAO.getTime()){
            logger.error("登陆参数异常");
            return responseToClient(AppError.APP_ARGS_ERROR);
        }


        if (useTimeListAO.getPointType() < 0 ){
            logger.error("上报使用时间参数异常");
            return responseToClient(AppError.APP_ARGS_ERROR);
        }


        String token = packet.getToken();
        String phone = null;
        try {
            phone = TokenUtil.getEmail(token);
        } catch (TokenException e) {
            logger.error("获取当天使用次数参数异常",e);
            return responseToClient(AppError.APP_TOKEN_INVALID_ERROR);
        }

        User user = userService.getByPhone(phone);
        if (null == user){
            logger.error("获取当天使用次数参数异常");
            return responseToClient(AppError.APP_TOKEN_INVALID_ERROR);
        }

        //设置返回token
        this.token = user.getToken();


        List<UseTime> returnList = useTimeService.getUseTimeList(user.getId(), useTimeListAO.getPointType() ,useTimeListAO.getTime().toString());

        return responseToClientWithData(AppError.APP_OK,null);

    }



}
