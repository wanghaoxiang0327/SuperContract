package com.sskj.common.glide;


import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

@GlideModule
public class MyGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        OkHttpClient okhttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true) // 设置出现错误进行重新连接。
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .sslSocketFactory(HttpsUtil.getSslSocketFactory(),new HttpsUtil.UnSafeTrustManager())
                .hostnameVerifier(new HttpsUtil.UnSafeHostnameVerifier())
                .build();
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okhttpClient));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

}
