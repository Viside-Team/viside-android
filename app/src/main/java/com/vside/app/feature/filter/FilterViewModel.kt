package com.vside.app.feature.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vside.app.feature.common.data.ContentItem
import com.vside.app.feature.filter.data.request.FilteredContentRequest
import com.vside.app.feature.filter.repo.FilterRepository
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.ContentItemClickListener
import com.vside.app.util.common.handleApiResponse
import com.vside.app.util.lifecycle.SingleLiveEvent
import kotlinx.coroutines.flow.collect
import java.math.BigInteger

class FilterViewModel(private val filterRepository: FilterRepository): BaseViewModel(), ContentItemClickListener {
    private val _contentList = MutableLiveData<List<ContentItem>>()
    val contentList: LiveData<List<ContentItem>> = _contentList

    suspend fun getKeywordsGroupedByCategory(onGetSuccess: () -> Unit, onGetFail: () -> Unit) {
        filterRepository.getKeywordsGroupedByCategory(tokenBearer)
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

    suspend fun getFilteredContentList(filteredContentRequest: FilteredContentRequest, onGetSuccess: () -> Unit, onGetFail: () -> Unit) {
        filterRepository.getFilteredContentList(tokenBearer, filteredContentRequest)
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        _contentList.value = it.data?.contents?.map { ContentItem(it) }
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
        filterRepository.toggleContentScrap(tokenBearer, contentId)
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