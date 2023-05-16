package com.depayse.data.remote.model.response

import com.depayse.data.remote.model.ContentDTO
import com.google.gson.annotations.SerializedName

data class ScrapListResponse(
    @SerializedName("count") val totalCnt: Int,
    @SerializedName("contents") val contentList: List<ContentDTO>?
)
