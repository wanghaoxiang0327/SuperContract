package com.sskj.common.user.respositroy;

import android.arch.lifecycle.LiveData;

import com.lzy.okgo.OkGo;
import com.sskj.common.App;
import com.sskj.common.http.BaseHttpConfig;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;
import com.sskj.common.user.data.UserBean;
import com.sskj.common.user.data.UserDao;
import com.sskj.common.user.data.UserDataBase;

import io.reactivex.schedulers.Schedulers;

/**
 * @author Hey
 */
public class UserRepository {


    private UserDao userDao;


    public UserRepository() {
        UserDataBase userDataBase = UserDataBase.getINSTANCE(App.INSTANCE);
        userDao = userDataBase.getUserDao();
    }

    public LiveData<UserBean> getUser() {
        return userDao.getUser();
    }

    public void update() {
        OkGo.<HttpResult<UserBean>>post(BaseHttpConfig.BASE_URL + HttpConfig.USER_INFO)
                .execute(new JsonCallBack<HttpResult<UserBean>>() {
                    @Override
                    protected void onNext(HttpResult<UserBean> result) {
                        Schedulers.newThread().scheduleDirect(() -> {
                            userDao.insert(result.getData());
                        });

                    }
                });
    }

    public void clear(){
        Schedulers.newThread().scheduleDirect(() -> {
            userDao.clear();
        });

    }
}
