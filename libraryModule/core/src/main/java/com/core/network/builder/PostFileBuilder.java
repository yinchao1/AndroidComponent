package com.core.network.builder;

import com.core.network.request.PostFileRequest;
import com.core.network.request.RequestCall;

import java.io.File;

import okhttp3.MediaType;

/**
 * Created by Administrator on 2018/4/2.
 */

public class PostFileBuilder extends OkHttpRequestBuilder<PostFileBuilder> {
    private File file;
    private MediaType mediaType;

    public PostFileBuilder file(File file){
        this.file = file;
        return this;
    }

    public PostFileBuilder mediaType(MediaType mediaType){
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostFileRequest(url, tag, params, headers, file, mediaType, id).build();
    }
}
