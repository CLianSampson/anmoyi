package com.anmoyi.model.dao;

import com.anmoyi.model.po.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);


    List<Comment>  getCommentList(@Param("productId") int productId, @Param("fromSize") int fromSize, @Param("toSize") int toSize);
}