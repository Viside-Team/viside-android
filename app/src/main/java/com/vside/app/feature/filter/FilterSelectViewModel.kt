package com.vside.app.feature.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.ApiResponse
import com.vside.app.feature.filter.data.CategoryKeywordItem
import com.vside.app.feature.filter.repo.FilterRepository
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.KeywordItemClickListener
import com.vside.app.util.lifecycle.SingleLiveEvent
import kotlinx.coroutines.launch

class FilterSelectViewModel(private val filterRepository: FilterRepository): BaseViewModel(), KeywordItemClickListener {
    private val _allKeywordsGroupedByCategory = MutableLiveData<List<CategoryKeywordItem>>()
    val allKeywordsGroupedByCategory: LiveData<List<CategoryKeywordItem>> = _allKeywordsGroupedByCategory

    // 사용자의 입력을 받을 집합
    private val _selectedKeywordSet = MutableLiveData<Set<String>>(mutableSetOf())
    val selectedKeywordSet:LiveData<Set<String>> = _selectedKeywordSet

    fun getKeywordsGroupedByCategory() {
        viewModelScope.launch {
            val response = filterRepository.getKeywordsGroupedByCategory(tokenBearer)
            when(response) {
                is ApiResponse.Success -> {
                    _allKeywordsGroupedByCategory.value = response.data?.categories?.map { it1 -> CategoryKeywordItem(it1) }
                }
                else -> {
                    _toastFailThemeKeyword.value = "키워드 리스트 가져오기"
                }
            }
        }
    }

    fun addKeyword(keyword: String) {
        _selectedKeywordSet.value = selectedKeywordSet.value?.toMutableSet()?.apply { add(keyword) }
    }

    fun removeKeyword(keyword: String) {
        _selectedKeywordSet.value = selectedKeywordSet.value?.toMutableSet()?.apply { remove(keyword) }
    }

    fun setSelectedKeywords(keywordsSet: Set<String>?) {
        if(keywordsSet.isNullOrEmpty()) {
            _selectedKeywordSet.value = mutableSetOf()
            allKeywordsGroupedByCategory.value?.forEach { it1 ->
                it1.keywordItems.forEach { it2 ->
                    it2.isSelected.value = false
                }
            }
        }
        else {
            _selectedKeywordSet.value = mutableSetOf<String>().apply {
                addAll(keywordsSet)
            }
            allKeywordsGroupedByCategory.value?.forEach {
                it.keywordItems.forEach { keywordItem ->
                    keywordItem.isSelected.value = keywordsSet.contains(keywordItem.keyword)
                }
            }
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