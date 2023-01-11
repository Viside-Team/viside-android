package com.vside.app.feature.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vside.app.feature.content.repo.ContentRepository
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.handleApiResponse
import com.vside.app.util.lifecycle.SingleLiveEvent
import kotlinx.coroutines.flow.collect
import java.math.BigInteger

class ContentViewModel(private val contentRepository: ContentRepository): BaseViewModel() {
    var contentUrl = ""

    var contentId = BigInteger("0")
    val isLightBg = MutableLiveData<Boolean>()
    val contentImgUrl = MutableLiveData<String>()
    val isBookmarked = MutableLiveData<Boolean>()
    val isScrapClickable = MutableLiveData<Boolean>(true)

    val isContentImgCollapsed = MutableLiveData<Boolean>()

    suspend fun getContentDetail(contentId: BigInteger, onGetSuccess: () -> Unit, onGetFail: () -> Unit) {
        contentRepository.getContentDetail(tokenBearer, contentId)
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

    suspend fun toggleContentScrap(contentId: BigInteger, onPostSuccess: () -> Unit, onPostFail: () -> Unit) {
        contentRepository.toggleContentScrap(tokenBearer, contentId)
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        onPostSuccess()
                    },
                    onError = {
                        onPostFail()
                    }, onException = {
                        onPostFail()
                    }
                )
            }
    }

    private val _isBookmarkClicked = SingleLiveEvent<Void>()
    val isBookmarkClicked: LiveData<Void> = _isBookmarkClicked

    fun onBookmarkClick() {
        _isBookmarkClicked.call()
    }
}