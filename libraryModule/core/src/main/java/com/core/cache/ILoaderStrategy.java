package com.core.cache;

/**
 * Created by yinchao on 2018/9/21.
 * 图片通用接口
 */

public interface ILoaderStrategy {
    /**
     * 加载图片
     * @param options 加载图片所需参数，不同框架所需参数不同
     */
    void loadImage(LoaderOptions options);

    /**
     * 清理内存缓存
     */
    void clearMemoryCache();

    /**
     * 清理磁盘缓存
     */
    void clearDiskCache();
}
