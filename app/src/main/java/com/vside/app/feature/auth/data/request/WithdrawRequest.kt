package com.vside.app.feature.auth.data.request

import com.google.gson.annotations.SerializedName

data class WithdrawRequest(
    @SerializedName("snsId") val snsId: String
)
