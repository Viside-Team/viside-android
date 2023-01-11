package com.vside.app.feature.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vside.app.feature.common.data.Content
import com.vside.app.feature.mypage.repo.MyPageRepository
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.handleApiResponse
import com.vside.app.util.lifecycle.SingleLiveEvent
import kotlinx.coroutines.flow.collect

class BookShelfViewModel(private val myPageRepository: MyPageRepository) : BaseViewModel() {
    private val _scrapContentList = MutableLiveData<List<Content>>()
    val scrapList: LiveData<List<Content>> = _scrapContentList

    suspend fun getScrapList(onGetSuccess: () -> Unit, onGetFail: () -> Unit) {
        myPageRepository.getScrapList(tokenBearer)
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        _scrapContentList.value = it.data?.contentList
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

    private val _isBackClicked = SingleLiveEvent<Void>()
    val isBackClicked: LiveData<Void> = _isBackClicked

    fun onBackClick() {
        _isBackClicked.call()
    }
}