package com.baway.wangjiaqi.yuekao_demo02;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by admin on 2017/8/25.
 */

public class Utils {
    public static void sendOkHttpRequest(String path, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(path)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
