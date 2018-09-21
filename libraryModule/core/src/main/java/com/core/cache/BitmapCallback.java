package com.core.cache;

import android.graphics.Bitmap;

/**
 * Created by yinchao on 2018/9/21.
 * 图片加载完成回调
 */

public interface BitmapCallback {
    void onBitmapLoaded(Bitmap bitmap);
    void onBitmapFailed();
}
