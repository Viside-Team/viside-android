package com.vside.app.feature.home

import com.vside.app.feature.home.repo.HomeRepository
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.handleApiResponse
import kotlinx.coroutines.flow.collect

class HomeViewModel(private val homeRepository: HomeRepository): BaseViewModel() {
    suspend fun getHomeContentList(onGetSuccess: () -> Unit, onGetFail: () -> Unit) {
        homeRepository.getHomeContentList(tokenBearer)
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        onGetSuccess()
                    },
                    onError = {
                        onGetFail()
                    }, onException = {
                        onGetFail()
                    }
                )
            }
    }
}