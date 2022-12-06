package com.vside.app.feature.common.data

import com.google.gson.annotations.SerializedName

data class BasicMessageResponse(
    @SerializedName("message") val message: String?
)
