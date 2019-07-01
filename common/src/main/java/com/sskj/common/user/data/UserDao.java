package com.sskj.common.user.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface UserDao {


    @Query("select * from user_data")
    LiveData<List<UserBean>> getUsers();

    @Query("select * from user_data limit 1")
    LiveData<UserBean> getUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserBean userBean);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(UserBean userBean);

    @Query("delete from user_data")
    void clear();
}
