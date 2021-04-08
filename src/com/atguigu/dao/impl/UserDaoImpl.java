package com.atguigu.dao.impl;

import com.atguigu.dao.UserDao;
import com.atguigu.pojo.User;

public class UserDaoImpl extends BaseDao implements UserDao {

    @Override
    public User queryUserByUsername(String username) {
        //传入用户名 执行sql语句查询用户
        String sql = "select `id`,`username`,`password`,`email` from t_user where username = ? ";
        //返回一个
        return queryForOne(User.class,sql,username);
    }

    @Override
    public User queryUserByUsernameAndpassword(String username, String password) {
        //传入用户名和密码 执行sql语句查询用户
        String sql = "select `id`,`username`,`password`,`email` from t_user where username = ? and password = ?";
        //返回多个
        return queryForOne(User.class,sql,username,password);
    }

    @Override
    public int saveUser(User user) {
        //执行插入sq了语句
        String sql = "insert into t_user(`username`,`password`,`email`) values(?,?,?)";
        //执行语句
        return update(sql, user.getUsername(), user.getPassword(), user.getEmail());
    }
}
