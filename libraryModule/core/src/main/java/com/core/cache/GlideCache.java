package com.core.cache;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.AppGlideModule;

import java.io.File;

/**
 * Created by yinchao on 2018/4/26.
 * https://blog.csdn.net/guolin_blog/article/details/53759439
 * 设置图片缓存大小及位置
 */

@GlideModule
public class GlideCache extends AppGlideModule {
    //外部路径
    private String sdRootPath = Environment.getExternalStorageDirectory().getPath();
    //手机app路径
    private String appRootPath = null;
    //缓存大小
    private int diskCacheSizeBytes = 1024 * 1024 * 20; // 20 MB

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        super.applyOptions(context, builder);
        this.appRootPath = context.getCacheDir().getPath();
        String packageName = context.getPackageName();
        builder.setDiskCache( new DiskLruCacheFactory( getStorageDirectory() + File.separator + packageName + File.separator + "Cache", diskCacheSizeBytes ));
    }

    private String getStorageDirectory(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? sdRootPath : appRootPath;
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
    }
}
