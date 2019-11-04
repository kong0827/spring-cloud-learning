package com.kxj.springcloud.service.impl;

import com.kxj.springcloud.entity.User;
import com.kxj.springcloud.service.UserService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author kongxiangjin
 * @Date 2019/11/4 16:44
 * @Version 1.0
 **/

@Service
public class UserServiceImpl implements UserService {

    private static List<User> userList = new ArrayList();
    static {
        userList.add(new User(1, "张三", 20));
        userList.add(new User(2, "李四", 21));
        userList.add(new User(3, "王五", 22));
        userList.add(new User(4, "哈哈", 23));
    }

    @Override
    public List<User> getAllUser() {
        return userList;
    }

    @Override
    public User getUserById(int id) {
        User user = null;
        for (User u : userList) {
            if (id == u.getId()) {
                user = u;
            }
        }
        return user;
    }
}
