package com.vside.app.feature.auth.data.response

import com.google.gson.annotations.SerializedName

data class NicknameDuplicationCheckResponse(
    @SerializedName("nameCheck") val isDuplicated: Boolean
)
