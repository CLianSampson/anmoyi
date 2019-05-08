package com.anmoyi.web.ao;

import java.util.List;

public class CommentAO {

    private String content;

    private List<String> urls;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
