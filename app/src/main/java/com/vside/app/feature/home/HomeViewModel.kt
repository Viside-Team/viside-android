package com.vside.app.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.ApiResponse
import com.vside.app.feature.common.data.ContentItem
import com.vside.app.feature.home.repo.HomeRepository
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.ContentItemClickListener
import com.vside.app.util.lifecycle.SingleLiveEvent
import kotlinx.coroutines.launch
import java.math.BigInteger

class HomeViewModel(private val homeRepository: HomeRepository) : BaseViewModel(),
    ContentItemClickListener {
    private val _contentList = MutableLiveData<List<ContentItem>>()
    val contentList: LiveData<List<ContentItem>> = _contentList

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    fun getHomeContentList() =
        viewModelScope.launch {
            _isLoading.value = true
            val response = homeRepository.getHomeContentList()
            _isLoading.value = false
            when (response) {
                is ApiResponse.Success -> {
                    _contentList.value = response.data?.contents?.map { content -> ContentItem(content) }
                }
                else -> {
                    _toastFailThemeKeyword.value = "컨텐츠 가져오기"
                }
            }
        }

    fun refreshHomeContentList() =
        viewModelScope.launch {
            _isLoading.value = true
            val response = homeRepository.getHomeContentList()
            _isLoading.value = false
            when(response) {
                is ApiResponse.Success -> {
                    _contentList.value?.forEach { originalItem ->
                        val newContent = response.data?.contents?.find { it.contentId == originalItem.contentId }
                        if(originalItem.isBookmark.value != newContent?.isBookmark) { originalItem.isBookmark.value = newContent?.isBookmark }
                    }
                    _contentList.value = _contentList.value
                }
                else -> {
                    _toastFailThemeKeyword.value = "컨텐츠 가져오기"
                }
            }
        }


    fun getProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            val response = homeRepository.getProfile()
            _isLoading.value = false
            when (response) {
                is ApiResponse.Success -> {
                    _userName.value = response.data?.userName
                }
                else -> {
                    _toastFailThemeKeyword.value = "프로필 정보 가져오기"
                }
            }
        }
    }

    fun toggleScrapContent(contentItem: ContentItem) =
        viewModelScope.launch {
            if(contentItem.isScrapClickable.value == true) {
                contentItem.isScrapClickable.value = false
                val isBookmarked = contentItem.isBookmark.value
                isBookmarked?.let {
                    contentItem.isBookmark.value = !isBookmarked
                }
                val response =  homeRepository.toggleContentScrap(
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