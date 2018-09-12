package com.service.router;

/**
 * Initialization.
 * <p>
 *   基于注解，使用方便，源码简洁
     链式调用，api友好
     多路径支持
     结果回调，每次跳转都会回调跳转结果
     编译期处理注解，不影响运行时性能
     除了可以使用注解定义路由，还支持手动分配路由
     自定义拦截器，可以对路由进行拦截，比如登录判断和埋点处理
     自定义路由匹配规则，相比较其他路由框架，该项目并没有限制路由的写法，除了内置的几个匹配器，用户完全可以定义自己的规则
     支持隐式Intent跳转
     支持多模块使用，支持组件化开发
     不仅支持注解Activity，还支持注解Fragment
     支持Kotlin
     https://www.jianshu.com/p/f582c3893bed
 * </p>
 * Created by chenenyu on 2017/11/9.
 */
public class Configuration {
    boolean debuggable;
    String[] modules;

    private Configuration() {
    }

    public static class Builder {
        private boolean debuggable;
        private String[] modules;

        public Builder setDebuggable(boolean debuggable) {
            this.debuggable = debuggable;
            return this;
        }

        public Builder registerModules(String... modules) {
            this.modules = modules;
            return this;
        }

        public Configuration build() {
            if (modules == null || modules.length == 0) {
                throw new RuntimeException("You must call registerModules() to initialize Router.");
            }
            Configuration configuration = new Configuration();
            configuration.debuggable = this.debuggable;
            configuration.modules = this.modules;
            return configuration;
        }
    }
}
