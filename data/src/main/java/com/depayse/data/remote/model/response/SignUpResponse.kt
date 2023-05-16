package com.depayse.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("success") val success: Boolean
)
