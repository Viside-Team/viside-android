package com.viside.app.feature.auth.data.response

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("memberStatus") val isOurUser: Boolean?,
    @SerializedName("jwt") val jwtBearer: String?
)
