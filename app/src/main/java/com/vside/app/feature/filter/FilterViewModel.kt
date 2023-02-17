package com.vside.app.feature.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.ApiResponse
import com.vside.app.feature.common.data.ContentItem
import com.vside.app.feature.filter.data.request.FilteredContentRequest
import com.vside.app.feature.filter.repo.FilterRepository
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.ContentItemClickListener
import com.vside.app.util.lifecycle.SingleLiveEvent
import kotlinx.coroutines.launch
import java.math.BigInteger

class FilterViewModel(private val filterRepository: FilterRepository): BaseViewModel(), ContentItemClickListener {
    private val _contentList = MutableLiveData<List<ContentItem>>()
    val contentList: LiveData<List<ContentItem>> = _contentList

    // 실제로 요청을 보낼 키워드의 집합
    val selectedKeywordSet = MutableLiveData<Set<String>>(mutableSetOf())

    fun getFilteredContentList() {
        viewModelScope.launch {
            _isLoading.value = true
            val response = filterRepository.getFilteredContentList(
                tokenBearer,
                FilteredContentRequest(selectedKeywordSet.value?.toList() ?: listOf())
            )
            _isLoading.value = false
            when(response) {
                is ApiResponse.Success -> {
                    _contentList.value = response.data?.contents?.map { ContentItem(it) }
                }
                else -> {
                    _toastFailThemeKeyword.value = "컨텐츠 가져오기"
                }
            }
        }
    }

    fun toggleScrapContent(contentItem: ContentItem) {
        viewModelScope.launch {
            if(contentItem.isScrapClickable.value == true) {
                contentItem.isScrapClickable.value = false
                val isBookmarked = contentItem.isBookmark.value
                isBookmarked?.let {
                    contentItem.isBookmark.value = !isBookmarked
                }
                val response = filterRepository.toggleContentScrap(
                    tokenBearer,
                    contentItem.contentId ?: BigInteger("0")
                )
                contentItem.isScrapClickable.value = true
                when(response) {
                    is ApiResponse.Success -> {}
                    else -> {
                        _toastFailThemeKeyword.value = "스크랩 / 스크랩 취소"
                        contentItem.isBookmark.value = isBookmarked
                    }
                }
            }
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