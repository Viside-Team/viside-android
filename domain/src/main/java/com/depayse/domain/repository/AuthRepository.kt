package com.depayse.domain.repository

import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun signUp(
        name: String?,
        email: String?,
        loginType: String?,
        gender: String?,
        ageRange: String?,
        snsId: String?
    ) : Flow<ApiResponse<SignUpResponse>>

    suspend fun signIn(loginType: String?, snsId: String?)

    suspend fun nicknameDuplicationCheck(nickname: String)
}