package com.anmoyi.service;

import com.anmoyi.model.po.User;

public interface UserService {

    User getByPhone(String phone);

    void addUser(User user);

}
