package com.vside.vside.util.common

import android.app.Application
import com.vside.vside.util.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class VsideApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        setUpKoin()
    }

    private fun setUpKoin() {
        startKoin {
            androidLogger()
            androidContext(this@VsideApplication)
            modules(viewModelModule)
        }
    }
}