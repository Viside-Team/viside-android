package com.vside.app.feature.auth.repo

import com.depayse.data.remote.model.request.NicknameDuplicationCheckRequest
import com.depayse.data.remote.model.request.SignInRequest
import com.depayse.data.remote.model.request.SignUpRequest
import com.depayse.data.remote.service.AuthService
import com.google.gson.annotations.SerializedName
import com.vside.app.util.common.flowApiResponse

class AuthRepository(private val authService: AuthService) {
    suspend fun signUp(
        name: String?,
        email: String?,
        loginType: String?,
        gender: String?,
        ageRange: String?,
        snsId: String?
    ) = flowApiResponse(
        authService.signUp(
            SignUpRequest(
                name,
                email,
                loginType,
                gender,
                ageRange,
                snsId
            )
        )
    )

    suspend fun signIn(loginType: String?, snsId: String?) =
        flowApiResponse(authService.signIn(SignInRequest(loginType, snsId)))

    suspend fun nicknameDuplicationCheck(nickname: String) =
        authService.nicknameDuplicationCheck(NicknameDuplicationCheckRequest(nickname))
}