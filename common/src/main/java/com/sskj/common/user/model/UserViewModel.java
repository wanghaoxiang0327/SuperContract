package com.sskj.common.user.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.sskj.common.user.data.User;
import com.sskj.common.user.respositroy.UserRepository;

import javax.inject.Inject;

/**
 * @author Hey
 */
public class UserViewModel extends ViewModel {


    private UserRepository userRepository;

    public UserViewModel() {
        this.userRepository = new UserRepository();
    }

    public LiveData<User> getUser() {
        if (userRepository != null) {
            return userRepository.getUser();
        }
        return null;
    }


}
