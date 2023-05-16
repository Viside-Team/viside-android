package com.depayse.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("provider") val loginType: String?,
    @SerializedName("snsId") val snsId: String?
)
