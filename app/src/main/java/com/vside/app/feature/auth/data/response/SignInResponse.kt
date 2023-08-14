package com.vside.app.feature.auth.data.response

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("memberStatus") val isOurUser: Boolean?,
    @SerializedName("accessToken") val jwtAccessBearer: String?,
    @SerializedName("refreshToken") val refreshToken: String?
)
