package com.vside.app.feature.home.data.response

import com.google.gson.annotations.SerializedName
import com.vside.app.feature.common.data.Content

data class ContentListResponse(
    @SerializedName("Contents") val contents: List<Content>
)
