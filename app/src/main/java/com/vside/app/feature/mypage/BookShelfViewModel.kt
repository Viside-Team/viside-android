package com.vside.app.feature.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.depayse.data.remote.mapper.toDomain
import com.depayse.domain.entity.Content
import com.skydoves.sandwich.ApiResponse
import com.depayse.data.repository.MyPageRepositoryImpl
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.ContentItemClickListener
import com.vside.app.util.lifecycle.SingleLiveEvent
import kotlinx.coroutines.launch
import java.math.BigInteger

class BookShelfViewModel(private val myPageRepository: MyPageRepositoryImpl) : BaseViewModel(), ContentItemClickListener {
    private val _scrapContentList = MutableLiveData<MutableList<Content>>()
    val scrapContentList: LiveData<MutableList<Content>> = _scrapContentList

    fun getScrapList() {
        viewModelScope.launch {
            _isLoading.value = true
            val response = myPageRepository.getScrapList(tokenBearer)
            _isLoading.value = false
            when(response) {
                is ApiResponse.Success -> {
                    _scrapContentList.value = response.data?.contentList?.map { it1 -> it1.toDomain() }?.toMutableList()
                }
                else -> {
                    _toastFailThemeKeyword.value = "스크랩 리스트 가져오기"
                }
            }
        }
    }

    fun toggleScrapContent(contentItem: Content) {
        viewModelScope.launch {
            val itemIdx = _scrapContentList.value?.indexOf(contentItem) ?: -1
            var tempItem = contentItem.copy()
            val tempItemList = _scrapContentList.value?.toMutableList()
            if(tempItem.isScrapClickable) {
                tempItemList?.set(itemIdx, tempItem.copy(isScrapClickable = false))
                _scrapContentList.value = tempItemList

                tempItem = tempItem.copy(isBookmark = !tempItem.isBookmark)
                tempItemList?.set(itemIdx, tempItem)
                _scrapContentList.value = tempItemList

                val response = myPageRepository.toggleContentScrap(
                    tokenBearer,
                    contentItem.contentId ?: BigInteger("0")
                )
                tempItemList?.set(itemIdx, tempItem.copy(isScrapClickable = true))
                _scrapContentList.value = tempItemList
                when(response) {
                    is ApiResponse.Success -> {
                        deleteContent(contentItem)
                    }
                    else -> {
                        _toastFailThemeKeyword.value = "스크랩 / 스크랩 취소"

                        tempItem = tempItem.copy(isBookmark = !tempItem.isBookmark)
                        tempItemList?.set(itemIdx, tempItem)
                        _scrapContentList.value = tempItemList
                    }
                }
            }
        }
    }

    fun deleteContent(contentItem: Content) {
        _scrapContentList.value?.remove(contentItem)
        _scrapContentList.value = _scrapContentList.value
    }

    private val _isBackClicked = SingleLiveEvent<Void>()
    val isBackClicked: LiveData<Void> = _isBackClicked

    fun onBackClick() {
        _isBackClicked.call()
    }

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