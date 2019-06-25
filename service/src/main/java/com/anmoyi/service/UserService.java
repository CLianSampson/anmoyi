package com.anmoyi.service;

import com.anmoyi.model.po.User;

public interface UserService {

    User getByPhone(String phone);

    /**
     *
     * @param user
     * @return  返回是否第一次登陆
     */
    boolean addUser(User user);

    void update(User user);
}
