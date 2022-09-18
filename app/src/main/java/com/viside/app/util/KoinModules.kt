package com.viside.app.util

import com.viside.app.feature.MainViewModel
import com.viside.app.feature.auth.LoginViewModel
import com.viside.app.feature.auth.repo.AuthRepository
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
    viewModel { MainViewModel() }
    viewModel { LoginViewModel(get()) }
}

val repositoryModule = module {
    single { AuthRepository(get()) }
}