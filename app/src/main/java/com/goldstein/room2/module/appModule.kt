package com.goldstein.room2.module

import androidx.room.Room
import com.goldstein.room2.db.UserDatabase
import com.goldstein.room2.db.UserRepository
import com.goldstein.room2.viewmodel.UserViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {
    single { UserRepository(get()) }

    viewModel { UserViewModel(get()) }

    single { Room.databaseBuilder(get(), UserDatabase::class.java, "database-name").build() }
    single { get<UserDatabase>().userDao() }
}
