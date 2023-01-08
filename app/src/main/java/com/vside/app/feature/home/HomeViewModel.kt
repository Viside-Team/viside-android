package com.vside.app.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vside.app.feature.common.data.ContentItem
import com.vside.app.feature.home.repo.HomeRepository
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.HomeContentItemClickListener
import com.vside.app.util.common.handleApiResponse
import com.vside.app.util.lifecycle.SingleLiveEvent
import kotlinx.coroutines.flow.collect

class HomeViewModel(private val homeRepository: HomeRepository): BaseViewModel(),
    HomeContentItemClickListener {
    private val _contentList = MutableLiveData<List<ContentItem>>()
    val contentList: LiveData<List<ContentItem>> = _contentList

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    suspend fun getHomeContentList(onGetSuccess: () -> Unit, onGetFail: () -> Unit) {
        homeRepository.getHomeContentList(tokenBearer)
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        _contentList.value = it.data?.contents?.map { content ->
                            ContentItem.getInstanceFromContent(content)
                        }
                        onGetSuccess()
                    },
                    onError = {
                        onGetFail()
                    }
                    , onException = {
                        onGetFail()
                    }
                )
            }
    }

    suspend fun getProfile(onGetSuccess: () -> Unit, onGetFail: () -> Unit) {
        homeRepository.getProfile(tokenBearer)
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        _userName.value = it.data?.userName
                        onGetSuccess()
                    },
                    onError = {
                        onGetFail()
                    }
                    , onException = {
                        onGetFail()
                    }
                )
            }
    }

    // 클릭 이벤트들
    private val _isHomeContentItemClicked = SingleLiveEvent<ContentItem>()
    val isHomeContentItemClicked: LiveData<ContentItem> = _isHomeContentItemClicked

    override fun onHomeContentItemClickListener(item: ContentItem) {
        _isHomeContentItemClicked.value = item
    }

    private val _isHomeContentBookmarkClicked = SingleLiveEvent<ContentItem>()
    val isHomeContentBookmarkClicked: LiveData<ContentItem> = _isHomeContentBookmarkClicked

    override fun onHomeContentItemBookmarkClickListener(item: ContentItem) {
        _isHomeContentBookmarkClicked.value = item
    }
}