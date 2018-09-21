package com.core.cache;

import android.net.Uri;

import java.io.File;

/**
 * Created by yinchao on 2018/9/21.
 * 图片管理类,提供对外接口
 * 静态代理模式,开发者只需关心ImageLoader+LoaderOptions
 */

public class ImageLoader {
    private static ILoaderStrategy sLoader;
    private static volatile ImageLoader sInstance;

    private ImageLoader() {
    }

    //单例
    public static ImageLoader getInstance(){
        if(sInstance == null){
            synchronized (ImageLoader.class){
                if(sInstance == null){
                    sInstance = new ImageLoader();
                }
            }
        }
        return sInstance;
    }

    //提供图片加载框架的接口
    public void setGlobalImageLoader(ILoaderStrategy loader){
        if(loader != null){
            sLoader = loader;
        }
    }

    public LoaderOptions load(String path){
        return new LoaderOptions(path);
    }

    public LoaderOptions load(int drawable){
        return new LoaderOptions(drawable);
    }

    public LoaderOptions load(File file){
        return new LoaderOptions(file);
    }

    public LoaderOptions load(Uri uri){
        return new LoaderOptions(uri);
    }

    public void loadOptions(LoaderOptions options){
        if(sLoader == null){
            throw new NullPointerException("ILoaderStrategy loader implement can not be null");
        }else{
            sLoader.loadImage(options);
        }
    }

    public void clearMemoryCache(){
        sLoader.clearMemoryCache();
    }

    public void clearDiskCache(){
        sLoader.clearDiskCache();
    }
}
