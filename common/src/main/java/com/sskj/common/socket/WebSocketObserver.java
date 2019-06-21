package com.sskj.common.socket;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class WebSocketObserver implements Observer<String> {

    private Disposable disposable;

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onNext(String s) {
        Log.e("next", s);
    }

    @Override
    public void onComplete() {

    }

    public void dispose() {
        if (disposable != null) {
            disposable.dispose();
        }
    }


}
