package com.vside.app.feature.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vside.app.feature.common.data.Content
import com.vside.app.feature.filter.data.request.FilteredContentRequest
import com.vside.app.feature.filter.repo.FilterRepository
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.handleApiResponse
import kotlinx.coroutines.flow.collect

class FilterViewModel(private val filterRepository: FilterRepository): BaseViewModel() {
    private val _contentList = MutableLiveData<List<Content>>()
    val contentList: LiveData<List<Content>> = _contentList

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
                        _contentList.value = it.data?.contents
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