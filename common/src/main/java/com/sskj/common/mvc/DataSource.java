package com.sskj.common.mvc;

import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;
import com.sskj.common.CommonConfig;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public abstract class DataSource<T> implements IAsyncDataSource<List<T>> {
    private int page=1;
    private int size= 10;
    private boolean hasMore;

    public DataSource(){

    }

    public DataSource(int size) {
        this.size = size;
    }

    @Override
    public RequestHandle refresh(ResponseSender<List<T>> sender) throws Exception {
        return loadData(sender,1);
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<T>> sender) throws Exception {
        return loadData(sender,page++);
    }

    private RequestHandle loadData(ResponseSender<List<T>> sender,int page){
        Disposable disposable= bindData(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(data->{
                    if (data!=null&&data.size()==size){
                        hasMore=true;
                    }else {
                        hasMore=false;
                    }
                    sender.sendData(data);
                }, e-> sender.sendError(new Exception(e)));
        return new RequestHandle() {
            @Override
            public void cancle() {
                disposable.dispose();
            }

            @Override
            public boolean isRunning() {
                return false;
            }
        };
    }

    public abstract  Flowable<List<T>>  bindData(int page);

    @Override
    public boolean hasMore() {
        return hasMore;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
