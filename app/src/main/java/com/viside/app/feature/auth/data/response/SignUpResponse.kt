package com.viside.app.feature.auth.data.response

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("success") val success: Boolean
)
