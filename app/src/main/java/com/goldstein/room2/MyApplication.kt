package com.goldstein.room2

import android.app.Application
import com.goldstein.room2.module.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule) // Ensure your module is included here
        }
    }
}