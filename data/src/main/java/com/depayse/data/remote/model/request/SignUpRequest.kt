package com.depayse.data.remote.model.request

data class SignUpRequest(
    val name: String?,
    val email: String?,
    val loginType: String?,
    val gender: String?,
    val ageRange: String?,
    val snsId: String?
)
