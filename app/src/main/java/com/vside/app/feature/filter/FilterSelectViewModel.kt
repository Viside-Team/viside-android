package com.vside.app.feature.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vside.app.feature.filter.data.CategoryKeywordItem
import com.vside.app.feature.filter.data.response.KeywordsGroupedByCategoryResponse
import com.vside.app.feature.filter.repo.FilterRepository
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.KeywordItemClickListener
import com.vside.app.util.common.handleApiResponse
import com.vside.app.util.lifecycle.SingleLiveEvent
import kotlinx.coroutines.flow.collect

class FilterSelectViewModel(private val filterRepository: FilterRepository): BaseViewModel(), KeywordItemClickListener {
    private val _allKeywordsGroupedByCategory = MutableLiveData<List<CategoryKeywordItem>>()
    val allKeywordsGroupedByCategory: LiveData<List<CategoryKeywordItem>> = _allKeywordsGroupedByCategory

    // 사용자의 입력을 받을 집합
    val selectedKeywordSet = MutableLiveData<Set<String>>(mutableSetOf())

    suspend fun getKeywordsGroupedByCategory(onGetSuccess: () -> Unit, onGetFail: () -> Unit) {
        filterRepository.getKeywordsGroupedByCategory(tokenBearer)
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        _allKeywordsGroupedByCategory.value = it.data?.categories?.map { it1 -> CategoryKeywordItem(it1) }
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

    private val _isCompleteClicked = SingleLiveEvent<Void>()
    val isCompleteClicked: LiveData<Void> = _isCompleteClicked

    fun onCompleteClick() {
        _isCompleteClicked.call()
    }

    private val _isAllClearClicked = SingleLiveEvent<Void>()
    val isAllClearClicked: LiveData<Void> = _isAllClearClicked

    fun onAllClearClick() {
        _isAllClearClicked.call()
    }

    private val _isKeywordItemClicked = SingleLiveEvent<CategoryKeywordItem.KeywordItem>()
    val isKeywordItemClicked: LiveData<CategoryKeywordItem.KeywordItem> = _isKeywordItemClicked

    override fun onKeywordItemClickListener(item: CategoryKeywordItem.KeywordItem) {
        _isKeywordItemClicked.value = item
    }
}