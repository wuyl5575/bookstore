package com.atguigu.dao;

import com.atguigu.pojo.User;

/**
 * 定义一个接口去查询用户
 */
public interface UserDao {

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 反回null 说明没有这个用户
     */
    public User queryUserByUsername(String username);

    /**
     * 根据用户名密码查询用户信息
     * @param username
     * @param password
     * @return 返回null 说明用户名密码错误
     */
    public User queryUserByUsernameAndpassword(String username, String password);
    /**
     * 保存用户信息
     * @param user
     * @return 返回-1表示操作做失败，其他是sql语句影响的行数
     */
    public int saveUser(User user);
}
