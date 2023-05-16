package com.depayse.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("memberStatus") val isOurUser: Boolean?,
    @SerializedName("jwt") val jwtBearer: String?
)
