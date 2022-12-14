package com.vside.app.feature.content

import androidx.lifecycle.MutableLiveData
import com.vside.app.feature.content.repo.ContentRepository
import com.vside.app.feature.content.service.ContentService
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.handleApiResponse
import kotlinx.coroutines.flow.collect
import java.math.BigInteger

class ContentViewModel(private val contentRepository: ContentRepository): BaseViewModel() {
    val isContentImgCollapsed = MutableLiveData<Boolean>()

    val isLightBg = MutableLiveData<Boolean>()

    val isBookmarked = MutableLiveData<Boolean>()

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

    suspend fun toggleScrapContent(contentId: BigInteger, onGetSuccess: () -> Unit, onGetFail: () -> Unit) {
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
}