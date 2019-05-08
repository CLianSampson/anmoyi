package com.anmoyi.service;

import com.anmoyi.common.exception.ArgsException;
import com.anmoyi.service.vo.CommentVO;

import java.util.List;

public interface CommentService {

    void  addComment(int userId, String content, List<String> urls) throws ArgsException;


    List<CommentVO> getCommentList(int productId, int pageNum);
}
