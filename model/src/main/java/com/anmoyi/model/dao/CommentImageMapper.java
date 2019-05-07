package com.anmoyi.model.dao;

import com.anmoyi.model.po.CommentImage;

import java.util.List;

public interface CommentImageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CommentImage record);

    int insertSelective(CommentImage record);

    CommentImage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CommentImage record);

    int updateByPrimaryKey(CommentImage record);


    List<String> getImageUrls(int commentId);
}