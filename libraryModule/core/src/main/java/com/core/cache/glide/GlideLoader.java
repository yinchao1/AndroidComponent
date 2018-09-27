package com.core.cache.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.core.cache.BitmapCallback;
import com.core.cache.ILoaderStrategy;
import com.core.cache.LoaderOptions;

import java.security.MessageDigest;

/**
 * Created by yinchao on 2018/9/21.
 * Glide加载实现
 */

public class GlideLoader implements ILoaderStrategy {
    private volatile static GlideRequest<Drawable> glideRequest;
    private Context context;

    public GlideLoader(Context context){
        this.context = context;
    }

    public static GlideRequest getGlideRequst(Context context){
        if(glideRequest == null){
            synchronized (GlideLoader.class){
                if(glideRequest == null){
                    glideRequest = GlideApp.with(context).asDrawable();
                }
            }
        }
        return glideRequest;
    }

    @Override
    public void loadImage(LoaderOptions options) {
        if (options.url != null){
            getGlideRequst(this.context).load(options.url);
        }else if (options.file != null){
            getGlideRequst(this.context).load(options.file);
        }else if (options.drawableResId != 0){
            getGlideRequst(this.context).load(options.drawableResId);
        }else if (options.uri != null){
            getGlideRequst(this.context).load(options.uri);
        }
        //设置图片大小
        if(options.targetWidth > 0 && options.targetHeight > 0){
            getGlideRequst(this.context)
                    .apply(new RequestOptions().override(options.targetWidth, options.targetHeight));
        }
        //设置图片缩放
        if(options.isCenterCrop){
            getGlideRequst(this.context).centerCrop();
        }else if(options.isCenterInside){
            getGlideRequst(this.context).centerInside();
        }
        if(options.errorResId != 0){
            getGlideRequst(this.context).error(options.errorResId);
        }
        if(options.placeholderResId != 0){
            getGlideRequst(this.context).placeholder(options.placeholderResId);
        }
        if(options.bitmapAngle != 0){
            getGlideRequst(this.context).transform(new RadiusTransformation(options.bitmapAngle));
        }
        if(options.skipLocalCache){
            getGlideRequst(this.context).diskCacheStrategy(DiskCacheStrategy.NONE);
        }
        if(options.skipMemoryCache){
            getGlideRequst(this.context).skipMemoryCache(true);
        }
        if(options.targetView instanceof ImageView){
            getGlideRequst(this.context).into((ImageView)options.targetView);
        }else if(options.callback != null){
            getGlideRequst(this.context).into(new GlideTarget(options.targetView, options.callback));
        }
    }

    @Override
    public void clearMemoryCache() {
        if(Looper.myLooper() == Looper.getMainLooper()){
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    GlideApp.get(GlideLoader.this.context).clearMemory();
                }
            }.start();
        }else{
            GlideApp.get(this.context).clearMemory();
        }
    }

    @Override
    public void clearDiskCache() {
        if(Looper.myLooper() == Looper.getMainLooper()){
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    GlideApp.get(GlideLoader.this.context).clearDiskCache();
                }
            }.start();
        }else{
            GlideApp.get(this.context).clearDiskCache();
        }
    }

    class RadiusTransformation extends BitmapTransformation {
        float bitmapAngle = 0f;

        RadiusTransformation(float bitmapAngle){
            this.bitmapAngle = bitmapAngle;
        }

        @Override
        protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
            int width = toTransform.getWidth();
            int height = toTransform.getHeight();

            Bitmap bitmap = pool.get(width, height, Bitmap.Config.RGB_565);
            bitmap.setHasAlpha(true);
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(new BitmapShader(toTransform, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            canvas.drawRoundRect(new RectF(0, 0, width, height), bitmapAngle, bitmapAngle, paint);
            return bitmap;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof RadiusTransformation){
                RadiusTransformation other = (RadiusTransformation)obj;
                return bitmapAngle == other.bitmapAngle;
            }
            return false;
        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
            messageDigest.update(("RadiusTransformation" + bitmapAngle).getBytes());
        }
    }

    class GlideTarget extends CustomViewTarget<View, Bitmap> {
        private BitmapCallback callback;

        public GlideTarget(@NonNull View view, BitmapCallback callback) {
            super(view);
            this.callback = callback;
        }

        @Override
        protected void onResourceCleared(@Nullable Drawable placeholder) {

        }

        @Override
        public void onLoadFailed(@Nullable Drawable errorDrawable) {
            if(this.callback != null){
                this.callback.onBitmapFailed();
            }
        }

        @Override
        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
            if(this.callback != null){
                this.callback.onBitmapLoaded(resource);
            }
        }
    }

    /**
     TODO
     界面中使用
     String url = "http://ww2.sinaimg.cn/large/7a8aed7bgw1eutsd0pgiwj20go0p0djn.jpg";
     ImageLoader.getInstance()
     .load(url)
     .angle(80)
     .resize(400, 600)
     .centerCrop()
     .placeholder(R.mipmap.test)
     .error(R.mipmap.test)
     .skipLocalCache(true)
     .into(imageView);
     */
}
