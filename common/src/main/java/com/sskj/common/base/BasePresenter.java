package com.sskj.common.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.lzy.okgo.OkGo;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter<V extends  IBaseView> implements LifecycleObserver {

    protected V mView;

    private WeakReference<V> mReference;

    private CompositeDisposable requests=new CompositeDisposable();


    public void attachView(V view){
        mReference=new WeakReference<V>(view);
        this.mView = mReference.get();
    }


    public void addRequest(Disposable disposable){
        requests.add(disposable);
    }

    public void showLoading(){
        if (mView!=null){
            mView.showLoading();
        }
    }
    public void hideLoading(){
        if (mView != null) {
            mView.hideLoading();
        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void cancel(){

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destroy(){
        if (requests!=null){
            requests.dispose();
        }
        if (mReference!=null){
            mReference.clear();
            mView=null;
        }
        OkGo.getInstance().cancelTag(this);
    }






}
