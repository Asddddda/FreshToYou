package com.own.freshtoyou.data;

import android.content.Context;
import androidx.room.Room;
import java.util.List;
public class UserManager {
    private static MyDB mDb;
    public static MyDB getIntance(Context context) {
        if (mDb == null) {
            mDb = Room.databaseBuilder(context,
                    MyDB.class,
                    "user.db").build();
        }
        return mDb;
    }
    //新增
    public static synchronized void addUser(Context context, User user) {
        getIntance(context).userDao().addUser(user);
    }
    //查询
    public static synchronized User getUser(Context context, String userName) {
        return getIntance(context).userDao().getUser(userName);
    }
    //删除
    public static synchronized void deleteUser(Context context, User user) {
        getIntance(context).userDao().deleteUser(user);
    }
    //修改
    public static synchronized void updateUser(Context context, User user) {
        getIntance(context).userDao().updateUser(user);
    }
    //获取用户信息
    public static synchronized List<User> getUsers(Context context) {
        return getIntance(context).userDao().getUsers();
    }
}