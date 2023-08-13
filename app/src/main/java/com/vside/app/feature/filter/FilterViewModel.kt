package com.vside.app.feature.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.depayse.data.remote.mapper.toDomain
import com.depayse.domain.entity.Content
import com.skydoves.sandwich.ApiResponse
import com.vside.app.feature.filter.repo.FilterRepository
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.ContentItemClickListener
import com.vside.app.util.lifecycle.SingleLiveEvent
import kotlinx.coroutines.launch
import java.math.BigInteger

class FilterViewModel(private val filterRepository: FilterRepository): BaseViewModel(), ContentItemClickListener {
    private val _contentList = MutableLiveData<List<Content>>()
    val contentList: LiveData<List<Content>> = _contentList

    // 실제로 요청을 보낼 키워드의 집합
    val selectedKeywordSet = MutableLiveData<Set<String>>(mutableSetOf())

    fun getFilteredContentList() {
        viewModelScope.launch {
            _isLoading.value = true
            val response = filterRepository.getFilteredContentList(
                tokenBearer,
                selectedKeywordSet.value?.toList() ?: listOf()
            )
            _isLoading.value = false
            when(response) {
                is ApiResponse.Success -> {
                    _contentList.value = response.data?.contents?.map { it.toDomain() }
                }
                else -> {
                    _toastFailThemeKeyword.value = "컨텐츠 가져오기"
                }
            }
        }
    }

    fun toggleScrapContent(contentItem: Content) {
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

                val response = filterRepository.toggleContentScrap(
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