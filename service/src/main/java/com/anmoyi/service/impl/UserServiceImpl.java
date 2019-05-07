package com.anmoyi.service.impl;

import com.anmoyi.common.TokenUtil;
import com.anmoyi.model.dao.UserMapper;
import com.anmoyi.model.po.User;
import com.anmoyi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getByPhone(String phone) {
        return userMapper.getByPhone(phone);
    }


    @Override
    public void addUser(User user) {
        User userInDB = userMapper.getByPhone(user.getPhone());

        String token = TokenUtil.getToken(user.getPhone());
        user.setToken(token);

        if (null == userInDB){
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());

            userMapper.insertSelective(user);
        }else {
            user.setUpdateTime(new Date());


            userMapper.updateByPrimaryKeySelective(user);
        }




    }


}
