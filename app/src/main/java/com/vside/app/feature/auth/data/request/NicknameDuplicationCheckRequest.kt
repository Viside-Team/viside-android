package com.vside.app.feature.auth.data.request

import com.google.gson.annotations.SerializedName

data class NicknameDuplicationCheckRequest(
    @SerializedName("name") val nickname: String
)
