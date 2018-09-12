package com.service.base.router;

import android.content.Context;

import com.service.router.RouteInterceptor;
import com.service.router.RouteRequest;

/**
 * Created by yinchao on 2018/4/4.
 * 登录拦截器
 */

public class LoginInterceptor implements RouteInterceptor {
    @Override
    public boolean intercept(Context context, RouteRequest routeRequest) {
        //TODO
        //判断用户是否登录，如未登录，直接跳转到登录界面
        return true;//返回true表示拦截当前路由
    }
}
