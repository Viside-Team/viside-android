package com.depayse.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class WithdrawRequest(
    @SerializedName("snsId") val snsId: String
)
