package com.own.freshtoyou.data;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    public int userId;//用户Id
    public String userName;//用户姓名
    public String userAge;//用户年龄
}