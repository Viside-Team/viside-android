package com.vside.app.util

import com.vside.app.feature.MainViewModel
import com.vside.app.feature.SplashViewModel
import com.vside.app.feature.auth.LoginViewModel
import com.vside.app.feature.auth.SignUpViewModel
import com.vside.app.feature.auth.repo.AuthRepository
import com.vside.app.feature.auth.service.AuthService
import com.vside.app.feature.common.service.CommonService
import com.vside.app.feature.common.view.LoginDialogViewModel
import com.vside.app.feature.common.view.OneBtnDialogViewModel
import com.vside.app.feature.common.view.TwoBtnDialogViewModel
import com.vside.app.feature.content.ContentViewModel
import com.vside.app.feature.content.repo.ContentRepository
import com.vside.app.feature.content.service.ContentService
import com.vside.app.feature.filter.FilterSelectViewModel
import com.vside.app.feature.filter.FilterViewModel
import com.vside.app.feature.filter.repo.FilterRepository
import com.vside.app.feature.filter.service.FilterService
import com.vside.app.feature.home.HomeViewModel
import com.vside.app.feature.home.repo.HomeRepository
import com.vside.app.feature.home.service.HomeService
import com.vside.app.feature.mypage.BookShelfViewModel
import com.vside.app.feature.mypage.MyPageViewModel
import com.vside.app.feature.mypage.repo.MyPageRepository
import com.vside.app.feature.mypage.service.MyPageService
import com.vside.app.util.common.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single { createOkHttp(androidContext()) }
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
    single { AuthRepository(get()) }
    single { HomeRepository(get(), get()) }
    single { FilterRepository(get(), get()) }
    single { MyPageRepository(get(), get(), get()) }
    single { ContentRepository(get(), get()) }
}