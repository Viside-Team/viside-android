package com.viside.app.util

import com.viside.app.feature.MainViewModel
import com.viside.app.feature.auth.LoginViewModel
import com.viside.app.feature.auth.repo.AuthRepository
import com.viside.app.feature.filter.FilterViewModel
import com.viside.app.feature.home.HomeViewModel
import com.viside.app.feature.mypage.MyPageViewModel
import com.viside.app.util.common.createAuthService
import com.viside.app.util.common.createOkHttp
import com.viside.app.util.common.createRetrofit
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single { createOkHttp() }
    single { createRetrofit(get(), "http://43.200.221.188:8080") }

    single { createAuthService(get()) }
}

val viewModelModule = module {
    // 로그인 화면
    viewModel { LoginViewModel(get()) }

    // 메인 화면
    viewModel { MainViewModel() }
    viewModel { HomeViewModel() }
    viewModel { FilterViewModel() }
    viewModel { MyPageViewModel() }

}

val repositoryModule = module {
    single { AuthRepository(get()) }
}