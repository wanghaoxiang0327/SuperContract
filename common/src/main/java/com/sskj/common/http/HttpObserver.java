package com.sskj.common.http;

import com.hjq.toast.ToastUtils;
import com.sskj.common.base.BasePresenter;
import com.sskj.common.exception.LogoutException;
import com.sskj.common.rxbus.RxBus;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class HttpObserver<T> implements Observer<T> {

    private BasePresenter presenter;

    public HttpObserver(BasePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (presenter != null) {
            presenter.showLoading();
            presenter.addRequest(d);
        }
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
        onFinish();
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ApiException) {
            ToastUtils.show(((ApiException) e).getMsg());
        } else if (e instanceof LogoutException) {
            RxBus.getDefault().post(e);
        } else {
            e.printStackTrace();
        }
        onFinish();
    }

    @Override
    public void onComplete() {

    }

    public void onFinish() {
        presenter.hideLoading();
    }


    protected abstract void onSuccess(T t);


}
