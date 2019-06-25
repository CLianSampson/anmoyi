package com.anmoyi.web.dto;

import com.anmoyi.model.po.User;

public class LoginDTO {

    private User user;

    private boolean loginFlag;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLoginFlag() {
        return loginFlag;
    }

    public void setLoginFlag(boolean loginFlag) {
        this.loginFlag = loginFlag;
    }
}
