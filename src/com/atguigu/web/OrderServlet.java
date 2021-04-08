package com.atguigu.web;

import com.atguigu.pojo.Cart;
import com.atguigu.pojo.User;
import com.atguigu.service.OrderService;
import com.atguigu.service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderServlet extends BaseServlet {

    private OrderService orderService = new OrderServiceImpl();

    /**
     * 生成订单
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //先获取cart购物车队像
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        //获取UserId
        User loginuser = (User) req.getSession().getAttribute("user");

        if (loginuser == null) {
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
            return;
        }



        Integer userId = loginuser.getId();

        //调用orderService.createOrder(cart, userId);生成订单
        String orderId = orderService.createOrder(cart, userId);

        //保存到域中
        //req.setAttribute("orderId", orderId);
        //请求转发到/pages/cart/checkout.jsp
        //req.getRequestDispatcher("/pages/cart/checkout.jsp").forward(req, resp);

        req.getSession().setAttribute("orderId", orderId);
        resp.sendRedirect(req.getContextPath()+"/pages/cart/checkout.jsp");
    }
}
