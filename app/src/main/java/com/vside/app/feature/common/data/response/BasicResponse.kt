package com.vside.app.feature.common.data.response

import com.google.gson.annotations.SerializedName

data class BasicResponse(
    @SerializedName("status") val status: Boolean?,
    @SerializedName("message") val message: String?
)