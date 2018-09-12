package com.core.network.builder;

import com.core.network.request.RequestCall;
import com.core.network.request.PostStringRequest;

import okhttp3.MediaType;

/**
 * Created by Administrator on 2018/4/2.
 */

public class PostStringBuilder extends OkHttpRequestBuilder<PostStringBuilder> {
    private String content;
    private MediaType mediaType;

    public PostStringBuilder content(String content){
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType){
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostStringRequest(url, tag, params, headers, content, mediaType,id).build();
    }
}
