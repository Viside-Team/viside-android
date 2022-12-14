package com.vside.app.util

import com.vside.app.feature.MainViewModel
import com.vside.app.feature.auth.LoginViewModel
import com.vside.app.feature.auth.repo.AuthRepository
import com.vside.app.feature.common.view.OneBtnDialogViewModel
import com.vside.app.feature.common.view.TwoBtnDialogViewModel
import com.vside.app.feature.content.ContentViewModel
import com.vside.app.feature.content.repo.ContentRepository
import com.vside.app.feature.filter.FilterSelectViewModel
import com.vside.app.feature.filter.FilterViewModel
import com.vside.app.feature.filter.repo.FilterRepository
import com.vside.app.feature.home.HomeViewModel
import com.vside.app.feature.home.repo.HomeRepository
import com.vside.app.feature.mypage.BookShelfViewModel
import com.vside.app.feature.mypage.MyPageViewModel
import com.vside.app.feature.mypage.repo.MyPageRepository
import com.vside.app.util.common.createAuthService
import com.vside.app.util.common.createOkHttp
import com.vside.app.util.common.createRetrofit
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

    // 필터 화면
    viewModel { FilterSelectViewModel() }

    // 마이 페이지 화면
    viewModel { BookShelfViewModel() }

    // 다이얼로그
    viewModel { TwoBtnDialogViewModel() }
    viewModel { OneBtnDialogViewModel() }

    // 컨텐츠 상세 화면
    viewModel { ContentViewModel() }

}

val repositoryModule = module {
    single { AuthRepository(get()) }
    single { HomeRepository(get()) }
    single { FilterRepository(get()) }
    single { MyPageRepository(get(), get()) }
    single { ContentRepository(get()) }
}