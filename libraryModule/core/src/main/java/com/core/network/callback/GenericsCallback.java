package com.core.network.callback;

import java.lang.reflect.ParameterizedType;

import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/2.
 */

public abstract class GenericsCallback<T> extends Callback<T> {
    IGenericsSerializator mGenericsSerializator;
    public GenericsCallback(IGenericsSerializator serializator) {
        mGenericsSerializator = serializator;
    }

    @Override
    public T parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if(entityClass == String.class){
            return (T)string;
        }
        T bean = mGenericsSerializator.transform(string, entityClass);
        return bean;
    }
}
