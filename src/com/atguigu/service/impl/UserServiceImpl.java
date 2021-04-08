package com.atguigu.service.impl;

import com.atguigu.dao.UserDao;
import com.atguigu.dao.impl.UserDaoImpl;
import com.atguigu.pojo.User;
import com.atguigu.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    @Override
    public void registUser(User user) {
        //注册保存用户
        userDao.saveUser(user);
    }

    @Override
    public User login(User user) {
        //登录  返回查询的对象
        //根据用户名密码查询用户信息
        return userDao.queryUserByUsernameAndpassword(user.getUsername(),user.getPassword());
    }

    @Override
    public boolean existsUsername(String usrname) {
        if (userDao.queryUserByUsername(usrname) == null){
            //等于空没查到
            return false;
        }

        return true;
    }
}
