package com.atguigu.web;

import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.service.impl.UserServiceImpl;
import com.atguigu.utils.WebUtils;
import com.google.gson.Gson;
import org.apache.commons.beanutils.BeanUtils;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;


/**
 * @author HP
 */
public class UserServlet extends BaseServlet {

    private UserService userService = new UserServiceImpl();


    /**
     * 处理登录功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("处理登录");

        //1.获取请求参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        //调用userservice.login 方法
        User loginUser = userService.login(new User(null, username, password, null));

        if(loginUser == null) {
            //把错误信息，和回显的表单，保存到request域中
            req.setAttribute("msg", "用户名或密码错误");
            req.setAttribute("username", username);

            //跳回登录页面
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
        } else {
            //保存用户登录后的信息到session域中
            req.getSession().setAttribute("user",loginUser);
            //登录成功跳转到登录成功的页面 login_success.jsp
            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req, resp);
        }


    }

    /**
     * 处理注册功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("zhuce");
        req.setCharacterEncoding("utf8");

        // 获取 Session 中的验证码
        String token = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        // 删除 Session 中的验证码
        req.getSession().removeAttribute(KAPTCHA_SESSION_KEY);

        //1.获取请求参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");

        User user = WebUtils.copyParamToBean(req.getParameterMap(), new User());

        //2.检查 验证码是否正确
        if (token!=null && token.equalsIgnoreCase(code)) {
            //3.检查用户是否可用
            if (userService.existsUsername(username)) {
                //用户名不可用
                //System.out.println("用户名[" + username +"]已存在");

                //把回显信息保存到request域中
                req.setAttribute("msg", "用户名已存在");
                req.setAttribute("username", username);
                req.setAttribute("email", email);

                req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
            }else {
                //用户名可用
                //注册
                userService.registUser(new User(null, username, password, email));
                //跳转到注册页面
                req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req, resp);
            }
        } else {
            //把回显信息保存到request域中
            req.setAttribute("msg", "验证码错误");
            req.setAttribute("username", username);
            req.setAttribute("email", email);
            //跳回注册页面
            //System.out.println("验证码错误"+code);
            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
        }

    }



    /**
     * 注销
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1、销毁 Session 中用户登录的信息（或者销毁 Session）
        req.getSession().invalidate();
        //2、重定向到首页（或登录页面）。
        resp.sendRedirect(req.getContextPath());
    }




    /**
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void ajaxExistsUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求参数username
        String username = req.getParameter("username");
        //调用userService.existsUsUsername();
        boolean existsUsername = userService.existsUsername(username);
        //把返回的结果封装成为 map 对象
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("existsUsername",existsUsername);
        //转换为json对象
        Gson gson = new Gson();
        String json = gson.toJson(resultMap);
        //响应的字符输出流
        resp.getWriter().write(json);

    }





}
