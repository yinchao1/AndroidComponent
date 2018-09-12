package com.service.base.router;

/**
 * Created by Administrator on 2018/4/4.
 */

public class RouterConstant {
    //拦截器
    /**
     * 登录拦截器
     */
    public static final String LOGIN_INTERCEPTORS = "LoginInterceptor";


    //路由URL
    /**
     * 用户模块
     */
    public interface UserModule{
        //个人中心
        String USER_CENTER_DIRECT = "userCenter";
        String USER_CENTER_SCHEMA = "xiaomaigui://user/userCenter";
    }

    /**
     * 订单模块
     */
    public interface OrderModule{
        //我的订单
        String USER_ORDER_DIRECT = "userOrder";
    }
}
