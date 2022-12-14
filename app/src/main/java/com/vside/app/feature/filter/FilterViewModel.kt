package com.vside.app.feature.filter

import com.vside.app.feature.filter.data.request.FilteredContentRequest
import com.vside.app.feature.filter.repo.FilterRepository
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.handleApiResponse
import kotlinx.coroutines.flow.collect

class FilterViewModel(private val filterRepository: FilterRepository): BaseViewModel() {
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