package com.vside.app.feature.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.ApiResponse
import com.vside.app.feature.common.data.ContentItem
import com.vside.app.feature.mypage.repo.MyPageRepository
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.ContentItemClickListener
import com.vside.app.util.lifecycle.SingleLiveEvent
import kotlinx.coroutines.launch
import java.math.BigInteger

class BookShelfViewModel(private val myPageRepository: MyPageRepository) : BaseViewModel(), ContentItemClickListener {
    private val _scrapContentList = MutableLiveData<MutableList<ContentItem>>()
    val scrapContentList: LiveData<MutableList<ContentItem>> = _scrapContentList

    fun getScrapList() {
        viewModelScope.launch {
            _isLoading.value = true
            val response = myPageRepository.getScrapList()
            _isLoading.value = false
            when(response) {
                is ApiResponse.Success -> {
                    _scrapContentList.value = response.data?.contentList?.map { it1 -> ContentItem(it1) }?.toMutableList()
                }
                else -> {
                    _toastFailThemeKeyword.value = "스크랩 리스트 가져오기"
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
                val response = myPageRepository.toggleContentScrap(
                    contentItem.contentId ?: BigInteger("0")
                )
                contentItem.isScrapClickable.value = true
                when(response) {
                    is ApiResponse.Success -> {
                        deleteContent(contentItem)
                    }
                    else -> {
                        _toastFailThemeKeyword.value = "스크랩 / 스크랩 취소"
                        contentItem.isBookmark.value = isBookmarked
                    }
                }
            }
        }
    }

    fun deleteContent(contentItem: ContentItem) {
        _scrapContentList.value?.remove(contentItem)
        _scrapContentList.value = _scrapContentList.value
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