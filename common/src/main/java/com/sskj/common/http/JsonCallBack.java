package com.sskj.common.http;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.sskj.common.base.BasePresenter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;

public abstract class JsonCallBack<T> extends AbsCallback<T> {

    private BasePresenter presenter;

    public JsonCallBack(BasePresenter presenter) {
        this.presenter = presenter;
    }

    public JsonCallBack(){

    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        if (presenter != null) {
            presenter.showLoading();
        }
    }

    @Override
    public void onSuccess(Response<T> response) {
        onSuccess(response);
        onFinish();
    }

    @Override
    public void onError(Response<T> response) {
        super.onError(response);
        onFinish();
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
                    } else {
                        throw new ApiException(result.getMsg());
                    }
                }
            }
        }
        return null;
    }

    protected abstract void onSuccess(T result);

    public void onFinish(){
        if (presenter!=null){
            presenter.hideLoading();
        }
    };
}
