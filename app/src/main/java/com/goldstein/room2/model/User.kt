package com.goldstein.room2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "users"
)
data class User(
    @PrimaryKey(
        autoGenerate = true
    )
    val id: Long = 0,
    val name: String,
    val email: String,
    val age: String
)
