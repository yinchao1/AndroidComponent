package com.core.network.builder;

import com.core.network.request.RequestCall;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/2.
 */

public abstract class OkHttpRequestBuilder<T extends OkHttpRequestBuilder> {
    protected String url;
    protected Object tag;
    protected Map<String, String> headers;
    protected Map<String, String> params;
    protected int id;

    public T id(int id){
        this.id = id;
        return (T)this;
    }

    public T url(String url){
        this.url = url;
        return (T)this;
    }

    public T tag(Object tag){
        this.tag = tag;
        return (T)this;
    }

    public T headers(Map<String, String> headers){
        this.headers = headers;
        return (T)this;
    }

    public T addHeader(String key, String value){
        if(this.headers == null){
            this.headers = new LinkedHashMap<>();
        }
        this.headers.put(key, value);
        return (T)this;
    }

    public abstract RequestCall build();
}
