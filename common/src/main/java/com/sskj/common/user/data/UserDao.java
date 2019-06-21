package com.sskj.common.user.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface UserDao {


    @Query("SELECT * FROM　User")
    LiveData<User> getAllUser();

    @Query("SELECT * FROM　User LIMIT 1")
    LiveData<User> getUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(User user);

    @Query("Delete from USER")
    void clear();
}
