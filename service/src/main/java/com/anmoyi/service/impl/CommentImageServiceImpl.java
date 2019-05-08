package com.anmoyi.service.impl;

import com.anmoyi.model.dao.CommentImageMapper;
import com.anmoyi.model.po.CommentImage;
import com.anmoyi.service.CommentImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class CommentImageServiceImpl implements CommentImageService {

    @Autowired
    private CommentImageMapper commentImageMapper;

    @Override
    public void addCommentImage(CommentImage commentImage) {

        commentImage.setCreateTime(new Date());
        commentImage.setUpdateTime(new Date());
        commentImageMapper.insertSelective(commentImage);
    }


}
