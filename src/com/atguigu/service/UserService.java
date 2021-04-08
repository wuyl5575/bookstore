package com.atguigu.service;

import com.atguigu.pojo.User;

public interface UserService {
    /**
     * 注册用户
     * @param user
     */
    public void registUser(User user);

    /**
     * 登录
     * @param user
     * @return 如果返回null登录失败 返回有值则成功
     */
    public User login(User user);

    /**
     * 检查用户是否存在
     * @param usrname
     * @return 返回true存在  false表示不存在
     */
    public boolean existsUsername(String usrname);

}
