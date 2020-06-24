package com.kuliao.kuliaojk.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kuliao.kuliaojk.dao.UserDao
import com.kuliao.kuliaojk.data.User

@Database(entities = [User::class], version = 1,exportSchema = false)
abstract class AppDataBase : RoomDatabase(){

    abstract fun getUserDao(): UserDao
}