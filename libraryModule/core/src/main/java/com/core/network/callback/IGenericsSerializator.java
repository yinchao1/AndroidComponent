package com.core.network.callback;

/**
 * Created by Administrator on 2018/4/2.
 */

public interface IGenericsSerializator {
    <T> T transform(String response, Class<T> classOfT);
}
