package com.kxj.springcloud.service;

import com.kxj.springcloud.entity.User;

import java.util.List;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author kongxiangjin
 * @Date 2019/11/4 16:43
 * @Version 1.0
 **/
public interface UserService {

    public List<User> getAllUser();

    public User getUserById(int id);

}
