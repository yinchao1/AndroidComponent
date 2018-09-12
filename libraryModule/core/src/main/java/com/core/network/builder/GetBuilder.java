package com.core.network.builder;

import android.net.Uri;

import com.core.network.request.RequestCall;
import com.core.network.request.GetRequest;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2018/4/2.
 */

public class GetBuilder extends OkHttpRequestBuilder implements HasParamsable {

    protected String appendParams(String url, Map<String, String> params){
        if(url == null || params == null || params.isEmpty()){
            return url;
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        Set<String> keys = params.keySet();
        Iterator<String> iteartor = keys.iterator();
        while (iteartor.hasNext()){
            String key = iteartor.next();
            builder.appendQueryParameter(key, params.get(key));
        }
        return builder.build().toString();
    }

    @Override
    public OkHttpRequestBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    @Override
    public OkHttpRequestBuilder addParams(String key, String val) {
        if(this.params == null){
            this.params = new LinkedHashMap();
        }
        this.params.put(key, val);
        return this;
    }

    @Override
    public RequestCall build() {
        if(params != null){
            url = appendParams(url, params);
        }

        return new GetRequest(url, tag, params, headers, id).build();
    }

}
