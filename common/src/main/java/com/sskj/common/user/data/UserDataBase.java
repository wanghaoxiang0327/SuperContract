package com.sskj.common.user.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import com.sskj.common.R;

/**
 * 数据库
 *
 * @author Hey
 */
@Database(entities = {UserBean.class}, version = 1)
public abstract class UserDataBase extends RoomDatabase {

    private static UserDataBase INSTANCE;

    public abstract UserDao getUserDao();

    public static UserDataBase getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (UserDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), UserDataBase.class, context.getString(R.string.app_name))
//                             允许在主线程中开启查询
//                            .allowMainThreadQueries()
                            .addMigrations()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    /**
     * 数据库更新示例
     * 数据库版本 1->2 user表格新增了age列
     */
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE user " + " ADD COLUMN age INTEGER");
        }
    };

}
