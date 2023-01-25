package com.vside.app.feature.filter.data

import androidx.lifecycle.MutableLiveData
import com.vside.app.feature.filter.data.response.KeywordsGroupedByCategoryResponse

data class CategoryKeywordItem(
    val category: String,
    val keywordItems: List<KeywordItem>
) {
    constructor(categoryKeyword: KeywordsGroupedByCategoryResponse.CategoryKeyword) : this(
        categoryKeyword.category.getOrNull(0) ?: "",
        categoryKeyword.keywords.map { KeywordItem(it) })

    data class KeywordItem(
        val isSelected: MutableLiveData<Boolean>,
        val keyword: String
    ) {
        constructor(string: String) : this(MutableLiveData(false), string)
    }
}
