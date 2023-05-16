package com.depayse.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class NicknameDuplicationCheckRequest(
    @SerializedName("name") val nickname: String
)
