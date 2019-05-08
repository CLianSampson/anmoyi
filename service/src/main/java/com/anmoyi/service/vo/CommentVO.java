package com.anmoyi.service.vo;

import com.anmoyi.model.po.Comment;
import com.anmoyi.model.po.User;

import java.util.List;

public class CommentVO {

    private Comment comment;


    private List<String> urlsList;


    private User user;

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public List<String> getUrlsList() {
        return urlsList;
    }

    public void setUrlsList(List<String> urlsList) {
        this.urlsList = urlsList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
