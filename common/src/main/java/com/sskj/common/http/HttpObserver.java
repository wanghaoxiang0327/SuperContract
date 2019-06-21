package com.sskj.common.http;

import com.sskj.common.base.BasePresenter;

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
        e.printStackTrace();
        onFinish();
    }

    @Override
    public void onComplete() {

    }

    public void onFinish(){
        presenter.hideLoading();
    };

    protected abstract void onSuccess(T t);


}
