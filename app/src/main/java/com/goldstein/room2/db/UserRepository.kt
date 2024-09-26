package com.goldstein.room2.db

import com.goldstein.room2.model.User
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val userDao: UserDao
) {

    suspend fun insertUser(user: User){
        userDao.insertUser(user)
    }

    fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers()
    }

  /*  fun getUserById(user: User): User {
        return userDao.getUserById(user.id)
    }*/

    fun updateUser(user: User) {
        return userDao.updateUser(user)
    }

    fun deleteUser(user: User) {
        return userDao.deleteUser(user.id)
    }

    suspend fun deleteAll() {
        userDao.deleteAll()
    }

}