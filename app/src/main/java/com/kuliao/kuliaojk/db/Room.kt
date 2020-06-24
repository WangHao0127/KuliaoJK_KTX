package com.kuliao.kuliaojk.db

import androidx.room.Room
import com.kuliao.kuliaojk.AppContext

private const val DB_NAME = "user_db"

val room = Room.databaseBuilder(AppContext, AppDataBase::class.java, DB_NAME).build()

val userDao = room.getUserDao()