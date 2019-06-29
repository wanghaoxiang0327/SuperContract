package com.sskj.common.user.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.sskj.common.user.data.UserBean;
import com.sskj.common.user.respositroy.UserRepository;

/**
 * @author Hey
 */
public class UserViewModel extends ViewModel {


    private UserRepository userRepository;

    public UserViewModel() {
        this.userRepository = new UserRepository();
    }

    public LiveData<UserBean> getUser() {
        if (userRepository != null) {
            return userRepository.getUser();
        }
        return null;
    }

    public void update() {
        if (userRepository != null) {
            userRepository.update();
        }
    }

    public void clear(){
        if (userRepository != null) {
            userRepository.clear();
        }
    }

}
