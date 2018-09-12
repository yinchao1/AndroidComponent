package com.core.network.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/2.
 */

public abstract class BitmapCallback extends Callback<Bitmap>{
    @Override
    public Bitmap parseNetworkResponse(Response response, int id) throws Exception {
        return BitmapFactory.decodeStream(response.body().byteStream());
    }
}
