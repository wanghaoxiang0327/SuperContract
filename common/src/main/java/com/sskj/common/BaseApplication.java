package com.sskj.common;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.toast.ToastUtils;
import com.hjq.toast.style.ToastQQStyle;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.shizhefei.mvc.MVCHelper;
import com.sskj.common.mvc.LoadViewFactory;

import java.util.logging.Level;

import okhttp3.OkHttpClient;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initHttp();
        MultiDex.install(this);
        ToastUtils.initStyle(new ToastQQStyle());
        ToastUtils.init(this);
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> new ClassicsFooter(this));
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> new ClassicsHeader(this));
        MVCHelper.setLoadViewFractory(new LoadViewFactory());
        if (BuildConfig.DEBUG){
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
    }

    private void initHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor("http");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);
        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())
                .setRetryCount(3);
    }

}
