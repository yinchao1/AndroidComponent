package com.service.base;

import android.app.Application;

import com.core.logger.LogType;
import com.core.logger.Logger;
import com.service.router.Configuration;
import com.service.router.Router;

/**
 * Created by yinchao on 2018/4/3.
 */

public class BaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化路由
        Router.initialize(new Configuration.Builder()
             //调试模式，开启后会打印log
            .setDebuggable(BuildConfig.DEBUG)
             // 模块名(即project.name)，每个使用Router的module都要在这里注册
            .registerModules("app", "base")
            .build());

        //设置日志
        Logger.APP_PKG_NAME = this.getPackageName();//设置包名-默认的日志Tag
        Logger.LOG_OPEN = BuildConfig.DEBUG;//设置Log开关
        Logger.LOG_LEVEL = LogType.V;//设置Log等级, >= 这个配置的log才会显示

    }
}
