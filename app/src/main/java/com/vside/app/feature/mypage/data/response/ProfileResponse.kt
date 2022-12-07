package com.vside.app.feature.mypage.data.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("username") val userName: String
)
