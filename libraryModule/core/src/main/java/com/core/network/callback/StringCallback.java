package com.core.network.callback;


import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/2.
 */

public abstract class StringCallback extends Callback<String>{
    @Override
    public String parseNetworkResponse(Response response, int id) throws Exception {
        return response.body().string();
    }
}
