package com.sskj.common.user.respositroy;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.sskj.common.App;
import com.sskj.common.user.data.User;
import com.sskj.common.user.data.UserDao;
import com.sskj.common.user.data.UserDataBase;

import javax.inject.Inject;

/**
 * @author Hey
 */
public class UserRepository {


    private UserDao userDao;


    public UserRepository() {
        UserDataBase userDataBase = UserDataBase.getINSTANCE(App.INSTANCE);
        userDao = userDataBase.getUserDao();
    }


    public LiveData<User> getUser() {
        return userDao.getUser();
    }
}
