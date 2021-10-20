package com.own.freshtoyou.data;


import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {User.class}, version = 1)
public abstract class MyDB extends RoomDatabase {
    public abstract UserDao userDao();
}
