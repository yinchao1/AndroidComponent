package com.core.network.builder;

import com.core.network.request.OtherRequest;
import com.core.network.request.RequestCall;

import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/4/2.
 */

public class OtherRequestBuilder extends OkHttpRequestBuilder<OtherRequestBuilder>{

    private RequestBody requestBody;
    private String method;
    private String content;

    public OtherRequestBuilder(String method){
        this.method = method;
    }

    public OtherRequestBuilder requestBody(RequestBody requestBody){
        this.requestBody = requestBody;
        return this;
    }

    public OtherRequestBuilder requestBody(String content){
        this.content = content;
        return this;
    }

    @Override
    public RequestCall build() {
        return new OtherRequest(requestBody, content, method, url, tag, params, headers,id).build();
    }
}
