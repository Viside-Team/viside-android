package com.vside.app.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vside.app.feature.common.data.ContentItem
import com.vside.app.feature.home.repo.HomeRepository
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.ContentItemClickListener
import com.vside.app.util.common.handleApiResponse
import com.vside.app.util.lifecycle.SingleLiveEvent
import kotlinx.coroutines.flow.collect
import java.math.BigInteger

class HomeViewModel(private val homeRepository: HomeRepository): BaseViewModel(),
    ContentItemClickListener {
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
                        _contentList.value = it.data?.contents?.map { content -> ContentItem(content) }
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

    suspend fun toggleScrapContent(contentId: BigInteger, onPostSuccess: () -> Unit, onPostFail: () -> Unit) {
        homeRepository.toggleContentScrap(tokenBearer, contentId)
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        onPostSuccess()
                    },
                    onError = {
                        onPostFail()
                    },
                    onException = {
                        onPostFail()
                    }
                )
            }
    }

    // 클릭 이벤트들
    private val _isContentItemClicked = SingleLiveEvent<ContentItem>()
    val isContentItemClicked: LiveData<ContentItem> = _isContentItemClicked

    override fun onContentItemClickListener(item: ContentItem) {
        _isContentItemClicked.value = item
    }

    private val _isContentBookmarkClicked = SingleLiveEvent<ContentItem>()
    val isContentBookmarkClicked: LiveData<ContentItem> = _isContentBookmarkClicked

    override fun onContentItemBookmarkClickListener(item: ContentItem) {
        _isContentBookmarkClicked.value = item
    }
}