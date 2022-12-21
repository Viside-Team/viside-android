package com.vside.app.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vside.app.feature.common.HomeContentItemClickListener
import com.vside.app.feature.common.data.Content
import com.vside.app.feature.home.repo.HomeRepository
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.handleApiResponse
import com.vside.app.util.lifecycle.SingleLiveEvent
import kotlinx.coroutines.flow.collect

class HomeViewModel(private val homeRepository: HomeRepository): BaseViewModel(), HomeContentItemClickListener {
    val contentList = MutableLiveData<List<Content>>()

    suspend fun getHomeContentList(onGetSuccess: () -> Unit, onGetFail: () -> Unit) {
        homeRepository.getHomeContentList(tokenBearer)
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        contentList.value = it.data?.contents
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

    // 클릭 이벤트들
    private val _isHomeContentClicked = SingleLiveEvent<Void>()
    val isHomeContentClicked: LiveData<Void> = _isHomeContentClicked

    override fun onHomeContentClick(item: Content) {
        _isHomeContentClicked.call()
    }

    private val _isHomeContentBookmarkClicked = SingleLiveEvent<Void>()
    val isHomeContentBookmarkClicked: LiveData<Void> = _isHomeContentBookmarkClicked

    override fun onHomeContentBookmarkClick(item: Content) {
        _isHomeContentBookmarkClicked.call()
    }
}