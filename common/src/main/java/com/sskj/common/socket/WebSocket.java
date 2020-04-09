package com.sskj.common.socket;

import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocketListener;

/**
 * @author Hey
 * @update 新增连接失败重试
 * created at 2018/4/9 13:03
 */

public class WebSocket extends WebSocketListener {
    protected String message;
    protected okhttp3.WebSocket mWebSocket;
    protected MarketWebSocketListener mListener;
    protected String tag;
    protected String url;
    //连接失败重试次数
    protected int retryCount = 0;
    //最大重试次数
    protected int maxTryCount = 1000;

    protected boolean isUserCancel = false;

    public boolean isLog() {
        return isLog;
    }

    public void setLog(boolean log) {
        isLog = log;
    }

    protected boolean isLog = false;

    protected boolean isSSL = true;


    protected Map<String, WebSocketObserver> observerMap;



    Handler retryHandler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            init(url);
        }
    };


    public static WebSocket getInstance(String url, String tag, String message) {
        return new WebSocket(url, tag, message);
    }


    protected WebSocket(String url, String tag, String message) {
        this.url = url;
        this.tag = tag;
        this.message = message;
        observerMap = new HashMap<>();
        init(url);
    }

    public WebSocket(String url, String tag, String message, boolean isSSL) {
        this.url = url;
        if (isSSL) {
            initBySSL();
        } else {
            init(url);
        }
        this.tag = tag;
        this.message = message;
        observerMap = new HashMap<>();
    }


    @Override
    public void onOpen(okhttp3.WebSocket webSocket, Response response) {
        mWebSocket = webSocket;
        if (response.code() == 101) {
            if (isLog) {
                Log.e(tag + "WebSocket>>:success", "");
            }



            if (mWebSocket.send(message)) {
                if (isLog) {
                    Log.e(tag + "WebSocket>>:send", message);
                }
            }

            if (retryHandler != null) {
                retryHandler.removeCallbacks(runnable);
            }

        }
    }


    @Override
    public void onMessage(okhttp3.WebSocket webSocket, String text) {
        if (mListener != null) {
            mListener.onMessage(text);
        }
        Observable<String> observable = Observable.just(text)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .publish()
                .autoConnect();
        for (Observer observer : observerMap.values()) {
            observable.subscribe(observer);
        }

        if (isLog) {
            Log.e(tag + "WebSocket>>Msg>>", text);
        }

    }

    public WebSocket observer(String tag, WebSocketObserver observer) {
        if (!observerMap.containsKey(tag)) {
            observerMap.put(tag, observer);
        }
        return this;
    }

    public void disposeByTag(String tag) {
        if (observerMap != null) {
            if (observerMap.containsKey(tag)) {
                observerMap.get(tag).dispose();
                observerMap.remove(tag);
            }
            if (observerMap.size() == 0) {
                closeWebSocket();
            }
        }
    }


    @Override
    public void onClosing(okhttp3.WebSocket webSocket, int code, String reason) {
        mWebSocket.cancel();
        if (isLog) {
            Log.e(tag + "WebSocket>>Close:", "code" + code + reason);
        }

    }

    @Override
    public void onFailure(okhttp3.WebSocket webSocket, Throwable t, Response response) {
        if (!isUserCancel) {
            if (retryCount < maxTryCount) {
                retryHandler.postDelayed(runnable, 1000*10);
                if (isLog) {
                    Log.e(tag + "WebSocket>>retry:", retryCount + "");
                }
                retryCount++;
            }
        }

        if (webSocket != null) {
            webSocket.close(1000, "");
        }
        if (isLog) {
            Log.e(tag + "WebSocket>>Fail:", t.toString());
        }

    }


    /**
     * 初始化WebSocket服务器
     *
     * @param url 链接地址
     */
    private void init(String url) {
        if (isLog) {
            Log.e(tag + ">>>" + "WebSocket", "init");
        }
        isUserCancel = false;
        try {
//            SSLContext sc = SSLContext.getInstance("TLS");
//            OkHttpClient client = new OkHttpClient.Builder().
//                    connectTimeout(30, TimeUnit.SECONDS)
//                    .writeTimeout(30, TimeUnit.SECONDS)
//                    .readTimeout(30, TimeUnit.SECONDS)
//                    .hostnameVerifier(new HostnameVerifier() {
//                        @Override
//                        public boolean verify(String hostname, SSLSession session) {
//                            return true;
//                        }
//                    })
//                    .sslSocketFactory(getSSLSocketFactory())
//                    .build();
//            Request request = new Request.Builder().url(url).build();
//            client.newWebSocket(request, this);
//            client.dispatcher().executorService().shutdown();
            OkHttpClient client = new OkHttpClient.Builder().
                    connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
            Request request = new Request.Builder().url(url).build();
            client.newWebSocket(request, this);
            client.dispatcher().executorService().shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initBySSL() {
        if (isLog) {
            Log.e(tag + ">>>" + "WebSocket", "init");
        }
        isUserCancel = false;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            OkHttpClient client = new OkHttpClient.Builder().
                    connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    })
                    .sslSocketFactory(getSSLSocketFactory())
                    .build();
            Request request = new Request.Builder().url(url).build();
            client.newWebSocket(request, this);
            client.dispatcher().executorService().shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //获取这个SSLSocketFactory
    public static SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //获取TrustManager
    private static TrustManager[] getTrustManager() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };
        return trustAllCerts;
    }

    //获取HostnameVerifier
    public static HostnameVerifier getHostnameVerifier() {
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
        return hostnameVerifier;
    }

    /**
     * 发送消息
     *
     * @param message 消息
     * @return 是否成功
     */
    public boolean sendMessage(String message) {
        if (mWebSocket != null) {
            Log.e(tag, "sendMessage->" + message);
            return mWebSocket.send(message);
        } else {
            Log.e(tag, "sendMessage->" + message + "->Failed");
            return false;
        }
    }

    /**
     * 关闭
     */
    public void closeWebSocket() {
        this.mListener = null;
        observerMap.clear();
        if (mWebSocket != null) {
            isUserCancel = true;
            mWebSocket.close(1000, "close by user");
        }
        retryHandler.removeCallbacks(runnable);
    }

    public void setListener(MarketWebSocketListener listener) {
        this.mListener = listener;
    }


    public interface MarketWebSocketListener {
        void onMessage(String message);
    }
}
