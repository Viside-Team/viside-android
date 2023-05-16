package com.depayse.data.remote.model.response

import com.depayse.data.remote.model.ContentDTO
import com.google.gson.annotations.SerializedName

data class ContentListResponse(
    @SerializedName("contents") val contents: List<ContentDTO>
)
