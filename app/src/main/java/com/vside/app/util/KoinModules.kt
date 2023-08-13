package com.vside.app.util

import com.vside.app.feature.MainViewModel
import com.vside.app.feature.SplashViewModel
import com.vside.app.feature.auth.LoginViewModel
import com.vside.app.feature.auth.SignUpViewModel
import com.depayse.data.repository.AuthRepositoryImpl
import com.depayse.data.remote.service.AuthService
import com.depayse.data.remote.service.CommonService
import com.vside.app.feature.common.view.LoginDialogViewModel
import com.vside.app.feature.common.view.OneBtnDialogViewModel
import com.vside.app.feature.common.view.TwoBtnDialogViewModel
import com.vside.app.feature.content.ContentViewModel
import com.depayse.data.repository.ContentRepositoryImpl
import com.depayse.data.remote.service.ContentService
import com.vside.app.feature.filter.FilterSelectViewModel
import com.vside.app.feature.filter.FilterViewModel
import com.depayse.data.repository.FilterRepositoryImpl
import com.depayse.data.remote.service.FilterService
import com.vside.app.feature.home.HomeViewModel
import com.depayse.data.repository.HomeRepositoryImpl
import com.depayse.data.remote.service.HomeService
import com.vside.app.feature.mypage.BookShelfViewModel
import com.vside.app.feature.mypage.MyPageViewModel
import com.depayse.data.repository.MyPageRepositoryImpl
import com.depayse.data.remote.service.MyPageService
import com.vside.app.util.common.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single { createOkHttp() }
    single { createRetrofit(get(), "http://43.200.221.188:8080") }

    single { createService<AuthService>(get()) }
    single { createService<HomeService>(get()) }
    single { createService<FilterService>(get()) }
    single { createService<MyPageService>(get()) }
    single { createService<ContentService>(get()) }
    single { createService<CommonService>(get()) }
}

val viewModelModule = module {
    // 스플래시 화면
    viewModel { SplashViewModel(get()) }

    // 로그인 화면
    viewModel { LoginViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { LoginDialogViewModel(get()) }

    // 메인 화면
    viewModel { MainViewModel() }
    viewModel { HomeViewModel(get()) }
    viewModel { FilterViewModel(get()) }
    viewModel { MyPageViewModel(get()) }

    // 필터 화면
    viewModel { FilterSelectViewModel(get()) }

    // 마이 페이지 화면
    viewModel { BookShelfViewModel(get()) }

    // 다이얼로그
    viewModel { TwoBtnDialogViewModel() }
    viewModel { OneBtnDialogViewModel() }

    // 컨텐츠 상세 화면
    viewModel { ContentViewModel(get()) }

}

val repositoryModule = module {
    single { AuthRepositoryImpl(get()) }
    single { HomeRepositoryImpl(get(), get()) }
    single { FilterRepositoryImpl(get(), get()) }
    single { MyPageRepositoryImpl(get(), get(), get()) }
    single { ContentRepositoryImpl(get(), get()) }
}