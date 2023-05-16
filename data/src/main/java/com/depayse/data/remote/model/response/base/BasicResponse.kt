package com.depayse.data.remote.model.response.base

import com.google.gson.annotations.SerializedName

data class BasicResponse(
    @SerializedName("status") val status: Boolean?,
    @SerializedName("message") val message: String?
)