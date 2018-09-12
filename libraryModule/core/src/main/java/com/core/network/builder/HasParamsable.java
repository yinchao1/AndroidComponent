package com.core.network.builder;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/2.
 */

public interface HasParamsable {
    OkHttpRequestBuilder params(Map<String, String> params);
    OkHttpRequestBuilder addParams(String key, String val);
}
