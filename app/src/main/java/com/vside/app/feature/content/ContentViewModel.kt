package com.vside.app.feature.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.ApiResponse
import com.depayse.data.repository.ContentRepositoryImpl
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.lifecycle.SingleLiveEvent
import kotlinx.coroutines.launch
import java.math.BigInteger

class ContentViewModel(private val contentRepository: ContentRepositoryImpl): BaseViewModel() {
    var contentUrl = ""

    var contentId = BigInteger("0")
    val dateStr = MutableLiveData<String>()
    val title = MutableLiveData<String>()
    val isLightBg = MutableLiveData<Boolean>()
    val contentImgUrl = MutableLiveData<String>()
    val isBookmarked = MutableLiveData<Boolean>()
    val isScrapClickable = MutableLiveData<Boolean>(true)

    val isContentImgCollapsed = MutableLiveData<Boolean>()

    fun getContentDetail(contentId: BigInteger) {
        viewModelScope.launch {
            _isLoading.value = true
            val response = contentRepository.getContentDetail(tokenBearer, contentId)
            _isLoading.value = false
            when(response) {
                is ApiResponse.Success -> {
                    title.value = response.data?.title ?: ""
                    dateStr.value = response.data?.dateStr?.replace("-", ". ") ?: ""
                    isBookmarked.value = response.data?.isBookmark
                    contentImgUrl.value = response.data?.contentImgUrl
                    isLightBg.value = response.data?.isLightBg
                }
                else -> {
                    _toastFailThemeKeyword.value = "컨텐츠 정보 가져오기"
                }
            }
        }
    }

    fun toggleContentScrap() {
        viewModelScope.launch {
            if(isScrapClickable.value == true) {
                isScrapClickable.value = false
                val isBookmark = isBookmarked.value
                isBookmark?.let {
                    isBookmarked.value = !isBookmark
                }
                val response = contentRepository.toggleContentScrap(
                    tokenBearer,
                    contentId
                )
                isScrapClickable.value = true
                when(response) {
                    is ApiResponse.Success -> {}
                    else -> {
                        _toastFailThemeKeyword.value = "스크랩 / 스크랩 취소"
                        isBookmarked.value = isBookmark
                    }
                }
            }
        }

    }

    private val _isBookmarkClicked = SingleLiveEvent<Void>()
    val isBookmarkClicked: LiveData<Void> = _isBookmarkClicked

    fun onBookmarkClick() {
        _isBookmarkClicked.call()
    }

    private val _isBackClicked = SingleLiveEvent<Void>()
    val isBackClicked: LiveData<Void> = _isBackClicked

    fun onBackClick() {
        _isBackClicked.call()
    }
}