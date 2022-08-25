package com.viside.app.util.common

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.viside.app.R
import com.viside.app.util.networkModule
import com.viside.app.util.repositoryModule
import com.viside.app.util.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class VisideApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        setUpKoin()
        setUpKakao()
    }

    private fun setUpKoin() {
        startKoin {
            androidLogger()
            androidContext(this@VisideApplication)
            modules(networkModule, repositoryModule, viewModelModule)
        }
    }

    private fun setUpKakao() {
        KakaoSdk.init(this, getString(R.string.kakao_app_key))
    }
}