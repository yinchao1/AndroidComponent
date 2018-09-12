package com.core.network.builder;

import com.core.network.OkHttpUtils;
import com.core.network.request.RequestCall;
import com.core.network.request.OtherRequest;

/**
 * Created by Administrator on 2018/4/2.
 */

public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
