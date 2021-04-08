package com.atguigu.test;

import com.atguigu.dao.UserDao;
import com.atguigu.dao.impl.UserDaoImpl;
import com.atguigu.pojo.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDaoTest {
    UserDao userDao = new UserDaoImpl();

    @Test
    public void queryUserByUsername() {
        if (userDao.queryUserByUsername("admin1") == null){
            System.out.println("用户名可用");
        } else {
            System.out.println("用户名存在");
        }
    }

    @Test
    public void queryUserByUsernameAndpassword() {
        if (userDao.queryUserByUsernameAndpassword("admin1","admin") == null){
            System.out.println("用户名或密码错误，失败");
        } else {
            System.out.println("成功");
        }
    }

    @Test
    public void saveUser() {
        System.out.println(userDao.saveUser(new User(null, "wz168", "123456", "wzg168@qq.com")));
    }
}