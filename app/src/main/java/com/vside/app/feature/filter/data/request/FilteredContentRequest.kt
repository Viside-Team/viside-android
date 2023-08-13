package com.vside.app.feature.filter.data.request

import com.google.gson.annotations.SerializedName

data class FilteredContentRequest(
    @SerializedName("keywordList") val keywords: List<String>
)