package com.vside.app.feature.filter.data.response

import com.google.gson.annotations.SerializedName

data class KeywordsGroupedByCategoryResponse(
    @SerializedName("categories") val categories: List<CategoryKeyword>

) {
    data class CategoryKeyword(
        @SerializedName("category") val category: List<String>,
        @SerializedName("keywords") val keywords: List<String>
    )
}
