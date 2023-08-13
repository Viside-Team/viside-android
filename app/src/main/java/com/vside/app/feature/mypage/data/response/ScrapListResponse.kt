package com.vside.app.feature.mypage.data.response

import com.google.gson.annotations.SerializedName
import com.vside.app.feature.common.data.Content

data class ScrapListResponse(
    @SerializedName("count") val totalCnt: Int,
    @SerializedName("contents") val contentList: List<Content>?
)
