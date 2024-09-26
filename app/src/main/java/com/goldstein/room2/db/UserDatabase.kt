package com.goldstein.room2.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.goldstein.room2.model.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}