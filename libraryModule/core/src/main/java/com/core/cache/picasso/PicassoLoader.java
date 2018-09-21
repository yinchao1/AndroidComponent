package com.core.cache.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.core.cache.BitmapCallback;
import com.core.cache.ILoaderStrategy;
import com.core.cache.LoaderOptions;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.File;

/**
 * Created by yinchao on 2018/9/21.
 * Picasso加载实现
 */

public class PicassoLoader implements ILoaderStrategy {
    private volatile static Picasso sPicassoSingleton;
    private static LruCache sLruCache;
    private final String PICASSO_CACHE = "picasso-cache";
    private Context context;

    public PicassoLoader(Context context){
        this.context = context;
    }

    public static Picasso getPicasso(Context context){
        if(sPicassoSingleton == null){
            synchronized (PicassoLoader.class){
                if(sPicassoSingleton == null){
                    Picasso.Builder builder = new Picasso.Builder(context);
                    if(sLruCache == null){
                        sLruCache = new LruCache(context);
                    }
                    sPicassoSingleton = builder.memoryCache(sLruCache).build();
                }
            }
        }
        return sPicassoSingleton;
    }

    @Override
    public void loadImage(LoaderOptions options) {
        //设置图片路径
        RequestCreator requestCreator = null;
        if(options.url != null){
            requestCreator = getPicasso(this.context).load(options.url);
        }else if(options.file != null){
            requestCreator = getPicasso(this.context).load(options.file);
        }else if(options.drawableResId != 0){
            requestCreator = getPicasso(this.context).load(options.drawableResId);
        }else if(options.uri != null){
            requestCreator = getPicasso(this.context).load(options.uri);
        }
        if(requestCreator != null){
            throw new NullPointerException("requestCreator can not be null, please set image url");
        }
        //设置组件大小
        if(options.targetHeight > 0 && options.targetWidth > 0){
            requestCreator.resize(options.targetWidth, options.targetHeight);
        }
        //设置图片缩放
        if(options.isCenterCrop){
            requestCreator.centerCrop();
        }else if(options.isCenterInside){
            requestCreator.centerInside();
        }
        if(options.config != null){
            requestCreator.config(options.config);
        }
        if(options.errorResId != 0){
            requestCreator.error(options.errorResId);
        }
        if(options.placeholderResId != 0){
            requestCreator.placeholder(options.placeholderResId);
        }
        if(options.bitmapAngle != 0){
            requestCreator.transform(new PicassoTransformation(options.bitmapAngle));
        }
        if(options.skipLocalCache){
            requestCreator.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE);
        }
        if(options.skipNetCache){
            requestCreator.networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE);
        }
        if(options.degrees != 0){
            requestCreator.rotate(options.degrees);
        }
        if(options.targetView instanceof ImageView){
            requestCreator.into((ImageView)options.targetView);
        }else if(options.callback != null){
            requestCreator.into(new PicassoTarget(options.callback));
        }
    }

    @Override
    public void clearMemoryCache() {
        if(sLruCache != null){
            sLruCache.clear();
        }
    }

    @Override
    public void clearDiskCache() {
        File diskFile = new File(this.context.getCacheDir(), PICASSO_CACHE);
        if(diskFile.exists()){
            //删除该文件
        }
    }

    class PicassoTarget implements Target{
        BitmapCallback callback;

        protected PicassoTarget(BitmapCallback callback){
            this.callback = callback;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            if(this.callback != null){
                this.callback.onBitmapLoaded(bitmap);
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            if(this.callback != null){
                this.callback.onBitmapFailed();
            }
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    }

    class PicassoTransformation implements Transformation{
        private float bitmapAngle;

        protected PicassoTransformation(float corner){
            this.bitmapAngle = corner;
        }

        @Override
        public Bitmap transform(Bitmap source) {
            float roundPx = bitmapAngle;
            Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(output);
            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, source.getWidth(),source.getHeight());
            final RectF rectF = new RectF(rect);
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(source, rect, rect, paint);
            if(source != null && !source.isRecycled()){
                source.recycle();
            }
            return output;
        }

        @Override
        public String key() {
            return "bitmapAngle()";
        }
    }
}
