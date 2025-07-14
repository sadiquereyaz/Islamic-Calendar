package com.reyaz.islamiccalendar

import android.app.Application
import com.reyaz.islamiccalendar.di.appModule
import com.reyaz.islamiccalendar.di.localModule
import com.reyaz.islamiccalendar.di.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(appModule, localModule, remoteModule)
        }
    }
}