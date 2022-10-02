package com.vside.app.util.common

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.vside.app.R
import com.vside.app.util.networkModule
import com.vside.app.util.repositoryModule
import com.vside.app.util.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class VsideApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        setUpKoin()
        setUpKakao()
    }

    private fun setUpKoin() {
        startKoin {
            androidLogger()
            androidContext(this@VsideApplication)
            modules(networkModule, repositoryModule, viewModelModule)
        }
    }

    private fun setUpKakao() {
        KakaoSdk.init(this, getString(R.string.kakao_app_key))
    }
}