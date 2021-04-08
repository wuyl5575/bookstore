package com.atguigu.test;

import com.atguigu.pojo.User;

import java.lang.reflect.Method;

public class UserServletTest {
    public void login() {
        System.out.println("这是login");
    }

    public void regist() {
        System.out.println("这是regist");
    }
    public void updateUser() {
        System.out.println("这是updateUser");
    }
    public void updateUserPassword() {
        System.out.println("这是updateUserPassword");
    }

    public static void main(String[] args) {
        String action = "login";
        try {
            //反射:获取action业务鉴别字符串,获取相应的业务 方法反射对象
            Method method = UserServletTest.class.getDeclaredMethod(action);

            //调用目标方法业务
            method.invoke(new UserServletTest());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
