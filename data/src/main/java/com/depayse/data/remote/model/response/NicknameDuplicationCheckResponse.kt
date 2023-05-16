package com.depayse.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class NicknameDuplicationCheckResponse(
    @SerializedName("nameCheck") val isDuplicated: Boolean
)
