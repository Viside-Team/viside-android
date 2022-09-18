package com.viside.app.feature.auth.data.request

data class SignUpRequest(
    val name: String?,
    val email: String?,
    val loginType: String?,
    val gender: String?,
    val ageRange: String?,
    val snsId: String?
)
