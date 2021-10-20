package com.own.freshtoyou.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface UserDao {
    //插入
    @Insert
    void addUser(User... users);

    @Insert
    void addUser(User user);

    //修改
    @Update
    void updateUser(User user);

    //删除
    @Delete
    void deleteUser(User user);

    //获取User
    @Query("SELECT * FROM user WHERE userName=:userName")
    User getUser(String userName);

    //查询
    @Query("SELECT * FROM User")
    List<User> getUsers();
}