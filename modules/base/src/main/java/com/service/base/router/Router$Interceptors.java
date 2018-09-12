package com.service.base.router;


import com.service.router.InterceptorTable;
import com.service.router.RouteInterceptor;
import com.service.router.RouteTable;
import com.service.router.Router;
import com.service.router.TargetInterceptors;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by yinchao on 2018/4/3.
 * 路由表注册，拦截器定义及应用
 * 路由表和拦截器可以通过注解，也可通过自定义方式手动控制路由表，该方式与注解无冲突，可以同时使用
 */
public class Router$Interceptors {
    public final static Map<String, Class<?>> routerMap = new HashMap<>();
    public final static Map<Class<?>, String[]> interceptorsMap = new HashMap<>();

    static {
        //注册路由表
        Router.handleRouteTable(new RouteTable() {
            @Override
            public void handle(Map<String, Class<?>> map) {
                if(routerMap != null && !routerMap.isEmpty()){
                    map.putAll(routerMap);
                }
            }
        });

        //定义拦截器
        Router.handleInterceptorTable(new InterceptorTable() {
            @Override
            public void handle(Map<String, Class<? extends RouteInterceptor>> map) {
                map.put(RouterConstant.LOGIN_INTERCEPTORS, LoginInterceptor.class);
            }
        });

        //应用拦截器
        Router.handleTargetInterceptors(new TargetInterceptors() {
            @Override
            public void handle(Map<Class<?>, String[]> map) {
                if(interceptorsMap != null && !interceptorsMap.isEmpty()){
                    map.putAll(interceptorsMap);
                }
            }
        });
    }

}
