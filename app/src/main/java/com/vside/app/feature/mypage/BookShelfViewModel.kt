package com.vside.app.feature.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vside.app.feature.common.data.ContentItem
import com.vside.app.feature.mypage.repo.MyPageRepository
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.ContentItemClickListener
import com.vside.app.util.common.handleApiResponse
import com.vside.app.util.lifecycle.SingleLiveEvent
import kotlinx.coroutines.flow.collect
import java.math.BigInteger

class BookShelfViewModel(private val myPageRepository: MyPageRepository) : BaseViewModel(), ContentItemClickListener {
    private val _scrapContentList = MutableLiveData<List<ContentItem>>()
    val scrapList: LiveData<List<ContentItem>> = _scrapContentList

    suspend fun getScrapList(onGetSuccess: () -> Unit, onGetFail: () -> Unit) {
        myPageRepository.getScrapList(tokenBearer)
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        _scrapContentList.value = it.data?.contentList?.map { it1 -> ContentItem(it1) }
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

    suspend fun toggleScrapContent(contentId: BigInteger, onPostSuccess: () -> Unit, onPostFail: () -> Unit) {
        myPageRepository.toggleContentScrap(tokenBearer, contentId)
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

    private val _isBackClicked = SingleLiveEvent<Void>()
    val isBackClicked: LiveData<Void> = _isBackClicked

    fun onBackClick() {
        _isBackClicked.call()
    }

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