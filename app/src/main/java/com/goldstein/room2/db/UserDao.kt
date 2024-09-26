package com.goldstein.room2.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.goldstein.room2.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    //create user
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    //read users
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

   /* // Query to get user by ID
    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    fun getUserById(userId: Long): User*/

    //update user
    @Update
    fun updateUser(user: User)

    //delete user
    @Query("DELETE FROM users WHERE id = :userId")
    fun deleteUser(userId: Long)

    //deleteAll
    @Query("DELETE FROM users")
    suspend fun deleteAll()
}