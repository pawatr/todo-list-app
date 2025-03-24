package com.pawatr.todo_list_app

import android.app.Application
import com.pawatr.todo_list_app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ToDoListApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ToDoListApplication)
            modules(appModule)
        }
    }
}