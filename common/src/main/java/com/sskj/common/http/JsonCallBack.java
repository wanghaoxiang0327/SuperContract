package com.sskj.common.http;

import android.support.annotation.CallSuper;

import com.alibaba.fastjson.JSON;
import com.hjq.toast.ToastUtils;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.sskj.common.base.BasePresenter;
import com.sskj.common.exception.LogoutException;
import com.sskj.common.rxbus.RxBus;
import com.sskj.common.utils.MD5Util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public abstract class JsonCallBack<T> extends AbsCallback<T> {

    private BasePresenter presenter;

    public JsonCallBack(BasePresenter presenter) {
        this.presenter = presenter;
    }

    public JsonCallBack() {

    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        HttpParams httpParams = request.getParams();
        LinkedHashMap<String, List<String>> map = httpParams.urlParamsMap;
        Iterator<String> iterable = map.keySet().iterator();
        while (iterable.hasNext()) {
            String name = iterable.next();
            if (name.contains("pwd")) {
                List<String> values = map.get(name);
                for (int i = 0; i < values.size(); i++) {
                    String value=values.get(i);
                    String encodeValue= MD5Util.encry5(value);
                    values.set(i, encodeValue);
                }
            }
        }
        if (presenter != null) {
            presenter.showLoading();
        }
    }

    @Override
    public void onSuccess(Response<T> response) {
        onNext(response.body());
    }

    @Override
    public void onError(Response<T> response) {
        super.onError(response);
        if (response.getException() instanceof ApiException) {
            if (!((ApiException) response.getException()).getMsg().equals("当前版本为最新")){
                ToastUtils.show(((ApiException) response.getException()).getMsg());
            }
        } else if (response.getException() instanceof LogoutException) {
            RxBus.getDefault().postPre(response.getException());
        } else {
            response.getException().printStackTrace();
        }
    }


    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {
        T data;
        ResponseBody body = response.body();
        if (body != null) {
            Type genericSuperclass = getClass().getGenericSuperclass();
            Type type = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
            data = JSON.parseObject(body.string(), type);
            if (data == null) {
                throw new ApiException("数据解析失败");
            } else {
                if (data instanceof HttpResult) {
                    HttpResult result = (HttpResult) data;
                    if (result.getStatus() == BaseHttpConfig.OK) {
                        return data;
                    } else if (result.getStatus() == BaseHttpConfig.LOGOUT) {
                        throw new LogoutException(result.getMsg());
                    } else {
                        throw new ApiException(result.getMsg());
                    }
                }
            }
        }
        return null;
    }

    protected abstract void onNext(T result);

    @Override
    @CallSuper
    public void onFinish() {
        if (presenter != null) {
            presenter.hideLoading();
        }
    }

    ;
}
