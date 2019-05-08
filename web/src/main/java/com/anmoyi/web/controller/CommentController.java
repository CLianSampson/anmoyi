package com.anmoyi.web.controller;

import com.alibaba.fastjson.JSON;
import com.anmoyi.common.*;
import com.anmoyi.common.exception.TokenException;
import com.anmoyi.model.po.CommentImage;
import com.anmoyi.model.po.User;
import com.anmoyi.service.CommentImageService;
import com.anmoyi.service.CommentService;
import com.anmoyi.service.UserService;
import com.anmoyi.service.vo.CommentVO;
import com.anmoyi.web.ao.CommentAO;
import com.anmoyi.web.ao.CommentListAO;
import com.anmoyi.web.dto.UploadPictureDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;


@RestController
public class CommentController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);



    @Autowired
    private UserService userService;


    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentImageService commentImageService;

    /**
     * 评论
     * @param requestString
     * @return
     */
    @PostMapping(value = "/comment")
    public String comment(@RequestBody String requestString){
        logger.info("评论参数");


        logger.info(requestString);

        Packet packet = null;

        try {
            packet = JSON.parseObject(requestString, Packet.class);
        } catch (Exception e) {
            logger.error("评论参数异常\n" + e);
            return responseToClient(AppError.APP_JSON_INVALID_ERROR);
        }


        CommentAO commentAO = null;
        try {
            //packet.getData() 为jsonobject
            commentAO = JSON.parseObject(packet.getData().toString(), CommentAO.class);
        } catch (Exception e) {
            logger.error("评论参数异常",  e);
            return responseToClient(AppError.APP_ARGS_ERROR);
        }



        if (StringUtil.isNullOrBlank(commentAO.getContent())){
            logger.error("评论参数异常");
            return responseToClient(AppError.APP_ARGS_ERROR);
        }



        String token = packet.getToken();
        String phone = null;
        try {
             phone = TokenUtil.getEmail(token);
        } catch (TokenException e) {
            logger.error("评论参数异常",e);
            return responseToClient(AppError.APP_TOKEN_INVALID_ERROR);
        }

        User user = userService.getByPhone(phone);
        if (null == user){
            logger.error("评论参数异常");
            return responseToClient(AppError.APP_TOKEN_INVALID_ERROR);
        }

        this.token = user.getToken();


        if (null != commentAO.getUrls()){
            commentService.addComment(user.getId(),commentAO.getContent(),commentAO.getUrls());
        }else {
            commentService.addComment(user.getId(),commentAO.getContent(),null);
        }


        return responseToClient(AppError.APP_OK);

    }


    /**
     * 上传评论图片
     * @param multipartFile
     * @param
     * @return
     * @throws IOException
     */
    @PostMapping(value="/uploadImage")
    public String uploadHeadPicture(@RequestParam("file") MultipartFile multipartFile, @RequestHeader("token") String token) throws IOException {

        String phone = null;
        try {
            phone = TokenUtil.getEmail(token);
        } catch (TokenException e) {
            logger.error("上传评论图片参数异常",e);
            return responseToClient(AppError.APP_TOKEN_INVALID_ERROR);
        }

        User user = userService.getByPhone(phone);
        if (null == user){
            logger.error("上传评论图片参数异常");
            return responseToClient(AppError.APP_TOKEN_INVALID_ERROR);
        }

        this.token = user.getToken();

        logger.info("用户信息是:");
        logger.info(user.toString());


        String fileName = UUID.randomUUID() + this.getExtension(multipartFile.getOriginalFilename());

        long fileLength = multipartFile.getSize();
        if( fileLength > 2*1024*1024 ){
            return "size error";
        }


        String path = Const.UPLOAD_FILE_PATH;

        FileInputStream fileInputStream = (FileInputStream) multipartFile.getInputStream();
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path + File.separator + fileName));
        byte[] bs = new byte[1024];
        int len;
        while ((len = fileInputStream.read(bs)) != -1) {
            bos.write(bs, 0, len);
        }
        bos.flush();
        bos.close();



        //添加上传记录
        CommentImage commentImage = new CommentImage();
        commentImage.setUserId(user.getId());
        //此时还没有评论
        commentImage.setCommentId(0);
        commentImage.setImageUrl(File.separator + fileName);


        commentImageService.addCommentImage(commentImage);

        UploadPictureDTO uploadPictureDTO = new UploadPictureDTO();
        uploadPictureDTO.setIamgeUrl(File.separator + fileName);

        return responseToClientWithData(AppError.APP_OK, uploadPictureDTO);

    }


    /**
     *
     * @param file
     * @return .txt
     */
    public String getExtension(String file) {
        String extension = null;
        int i = file.lastIndexOf(".");
        if (i > 0 && i < file.length() - 1) {
            extension = file.substring(i).toLowerCase();
        }
        return extension;
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
