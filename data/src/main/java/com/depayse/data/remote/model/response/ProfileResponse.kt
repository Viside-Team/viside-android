package com.depayse.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("username") val userName: String
)
