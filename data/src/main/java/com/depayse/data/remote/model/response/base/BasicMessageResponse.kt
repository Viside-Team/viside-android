package com.depayse.data.remote.model.response.base

import com.google.gson.annotations.SerializedName

data class BasicMessageResponse(
    @SerializedName("message") val message: String?
)
