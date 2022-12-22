package com.vside.app.feature.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vside.app.feature.filter.data.response.KeywordsGroupedByCategoryResponse
import com.vside.app.feature.filter.repo.FilterRepository
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.handleApiResponse
import kotlinx.coroutines.flow.collect

class FilterSelectViewModel(private val filterRepository: FilterRepository): BaseViewModel() {
    private val _allKeywordsGroupedByCategory = MutableLiveData<List<KeywordsGroupedByCategoryResponse.CategoryKeyword>>()
    val allKeywordsGroupedByCategory: LiveData<List<KeywordsGroupedByCategoryResponse.CategoryKeyword>> = _allKeywordsGroupedByCategory

    private val _selectedKeywordList = MutableLiveData<Set<String>>()
    val selectedKeywordList: LiveData<Set<String>> = _selectedKeywordList

    suspend fun getKeywordsGroupedByCategory(onGetSuccess: () -> Unit, onGetFail: () -> Unit) {
        filterRepository.getKeywordsGroupedByCategory(tokenBearer)
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        _allKeywordsGroupedByCategory.value = it.data?.categories
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