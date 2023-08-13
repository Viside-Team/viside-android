package com.vside.app.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.depayse.data.remote.mapper.toDomain
import com.depayse.domain.entity.Content
import com.skydoves.sandwich.ApiResponse
import com.vside.app.feature.home.repo.HomeRepository
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.ContentItemClickListener
import com.vside.app.util.lifecycle.SingleLiveEvent
import kotlinx.coroutines.launch
import java.math.BigInteger

class HomeViewModel(private val homeRepository: HomeRepository) : BaseViewModel(),
    ContentItemClickListener {
    private val _contentList = MutableLiveData<List<Content>>()
    val contentList: LiveData<List<Content>> = _contentList

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    fun getHomeContentList() =
        viewModelScope.launch {
            _isLoading.value = true
            val response = homeRepository.getHomeContentList(tokenBearer)
            _isLoading.value = false
            when (response) {
                is ApiResponse.Success -> {
                    _contentList.value = response.data?.contents?.map { it1 -> it1.toDomain() }
                }
                else -> {
                    _toastFailThemeKeyword.value = "컨텐츠 가져오기"
                }
            }
        }

    fun refreshHomeContentList() =
        viewModelScope.launch {
            _isLoading.value = true
            val response = homeRepository.getHomeContentList(tokenBearer)
            _isLoading.value = false
            when(response) {
                is ApiResponse.Success -> {
                    val tempContentList = _contentList.value?.toMutableList()
                    tempContentList?.forEachIndexed { idx, originalItem ->
                        val newContent = response.data?.contents?.find { it.contentId == originalItem.contentId }
                        if(originalItem.isBookmark != newContent?.isBookmark) {
                            newContent?.isBookmark?.let {
                                tempContentList[idx] = originalItem.copy(isBookmark = it)
                            }
                        }
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
            val response = homeRepository.getProfile(tokenBearer)
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

    fun toggleScrapContent(contentItem: Content) =
        viewModelScope.launch {
            val itemIdx = _contentList.value?.indexOf(contentItem) ?: -1
            var tempItem = contentItem.copy()
            val tempItemList = _contentList.value?.toMutableList()
            if(tempItem.isScrapClickable) {
                tempItemList?.set(itemIdx, tempItem.copy(isScrapClickable = false))
                _contentList.value = tempItemList

                tempItem = tempItem.copy(isBookmark = !tempItem.isBookmark)
                tempItemList?.set(itemIdx, tempItem)
                _contentList.value = tempItemList

                val response =  homeRepository.toggleContentScrap(
                    tokenBearer,
                    contentItem.contentId ?: BigInteger("0")
                )

                tempItemList?.set(itemIdx, tempItem.copy(isScrapClickable = true))
                _contentList.value = tempItemList

                when(response) {
                    is ApiResponse.Success -> {}
                    else -> {
                        _toastFailThemeKeyword.value = "스크랩 / 스크랩 취소"

                        tempItem = tempItem.copy(isBookmark = !tempItem.isBookmark)
                        tempItemList?.set(itemIdx, tempItem)
                        _contentList.value = tempItemList
                    }
                }
            }
        }

    // 클릭 이벤트들
    private val _isContentItemClicked = SingleLiveEvent<Content>()
    val isContentItemClicked: LiveData<Content> = _isContentItemClicked

    override fun onContentItemClickListener(item: Content) {
        _isContentItemClicked.value = item
    }

    private val _isContentBookmarkClicked = SingleLiveEvent<Content>()
    val isContentBookmarkClicked: LiveData<Content> = _isContentBookmarkClicked

    override fun onContentItemBookmarkClickListener(item: Content) {
        _isContentBookmarkClicked.value = item
    }
}