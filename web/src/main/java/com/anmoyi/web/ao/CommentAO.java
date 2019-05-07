package com.anmoyi.web.ao;

import java.util.List;

public class CommentAO {

    private String content;

    private List<String> avatarUrlArry;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getAvatarUrlArry() {
        return avatarUrlArry;
    }

    public void setAvatarUrlArry(List<String> avatarUrlArry) {
        this.avatarUrlArry = avatarUrlArry;
    }


}
