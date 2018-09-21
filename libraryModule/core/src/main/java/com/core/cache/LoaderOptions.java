package com.core.cache;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;

import java.io.File;

/**
 * Created by yinchao on 2018/9/21.
 * 加载图片所需参数，不同框架参数不同
 */

public class LoaderOptions {
    //占位图片
    public int placeholderResId;
    //图片加载错误默认图
    public int errorResId;
    //缩放方式
    public boolean isCenterCrop;
    public boolean isCenterInside;
    //是否缓存到本地
    public boolean skipLocalCache;
    public boolean skipNetCache;
    public Bitmap.Config config = Bitmap.Config.RGB_565;
    //针对Fresco加载View时候的宽高
    public int targetWidth;
    public int targetHeight;
    //圆角弧度
    public float bitmapAngle;
    //旋转角度
    public float degrees;
    //占位图(Drawable)
    public Drawable placeholder;
    //targetView展示图片
    public View targetView;
    public BitmapCallback callback;
    //图片URL
    public String url;
    public File file;
    public int drawableResId;
    public Uri uri;

    public LoaderOptions(String url){
        this.url = url;
    }

    public LoaderOptions(File file){
        this.file = file;
    }

    public LoaderOptions(int drawableResId){
        this.drawableResId = drawableResId;
    }

    public LoaderOptions(Uri uri){
        this.uri = uri;
    }

    public void into(View targetView){
        this.targetView = targetView;
        ImageLoader.getInstance().loadOptions(this);
    }

    public void bitmap(BitmapCallback callback){
        this.callback = callback;
        ImageLoader.getInstance().loadOptions(this);
    }

    public LoaderOptions placeHolder(int placeholderResId){
        this.placeholderResId = placeholderResId;
        return this;
    }

    public LoaderOptions placeHolder(Drawable placeholder){
        this.placeholder = placeholder;
        return this;
    }

    public LoaderOptions error(int errorResId){
        this.errorResId = errorResId;
        return this;
    }

    public LoaderOptions centerCrop(){
        this.isCenterCrop = true;
        return this;
    }

    public LoaderOptions centerInside(){
        this.isCenterInside = true;
        return this;
    }

    public LoaderOptions config(Bitmap.Config config){
        this.config = config;
        return this;
    }

    public LoaderOptions resize(int targetWidth, int targetHeight){
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
        return this;
    }

    public LoaderOptions angle(float bitmapAngle){
        this.bitmapAngle = bitmapAngle;
        return this;
    }

    public LoaderOptions skipLocalCache(boolean skipLocalCache){
        this.skipLocalCache = skipLocalCache;
        return this;
    }

    public LoaderOptions skipNetCache(boolean skipNetCache){
        this.skipNetCache = skipNetCache;
        return this;
    }

    public LoaderOptions rotate(float degrees){
        this.degrees = degrees;
        return this;
    }
}
