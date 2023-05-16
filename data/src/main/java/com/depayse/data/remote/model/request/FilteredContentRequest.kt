package com.depayse.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class FilteredContentRequest(
    @SerializedName("keywordList") val keywords: List<String>
)