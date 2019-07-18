package com.bupt.ZigbeeResolution.service;

import com.bupt.ZigbeeResolution.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public Integer getUser(String userName){
        return userMapper.getUser(userName);
    }
}
