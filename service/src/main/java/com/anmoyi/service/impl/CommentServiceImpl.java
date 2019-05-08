package com.anmoyi.service.impl;

import com.anmoyi.common.Const;
import com.anmoyi.model.dao.CommentImageMapper;
import com.anmoyi.model.dao.CommentMapper;
import com.anmoyi.model.dao.UserMapper;
import com.anmoyi.model.po.Comment;
import com.anmoyi.model.po.CommentImage;
import com.anmoyi.model.po.User;
import com.anmoyi.service.CommentService;
import com.anmoyi.service.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{


    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentImageMapper imageMapper;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public void addComment(int userId, String content, List<String> urls) {

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUserId(userId);
        comment.setCreateTime(new Date());


        comment.setProductId(Const.AN_MO_YI_ID);

        commentMapper.insertSelective(comment);



        if (null == urls){
            return;
        }


        int commemtId = comment.getId();

        for ( String temp: urls) {
            CommentImage commentImage = new CommentImage();
            commentImage.setCommentId(commemtId);
            commentImage.setImageUrl(temp);
            commentImage.setCreateTime(new Date());

            imageMapper.insertSelective(commentImage);
        }

    }


    @Override
    public List<CommentVO> getCommentList(int productId , int pageNum) {


        int frommSize = Const.PAGE_SIZE * (pageNum - 1) ;
        int toSzie = Const.PAGE_SIZE * pageNum ;
        List<Comment> list = commentMapper.getCommentList(productId, frommSize, toSzie);

        if (list.isEmpty()){
            return null;
        }

        List<CommentVO> returnList = new ArrayList<>();

        for (Comment temp : list) {
            CommentVO commentVO = new CommentVO();
            commentVO.setComment(temp);



            //用户信息
            User user = userMapper.selectByPrimaryKey(temp.getUserId());
            //token不返回给前端
            user.setToken(null);
            commentVO.setUser(user);


            List<String> urls = imageMapper.getImageUrls(temp.getId());
            if (!urls.isEmpty()){
                commentVO.setUrlsList(urls);
            }

            returnList.add(commentVO);
        }



        return returnList;
    }
}
