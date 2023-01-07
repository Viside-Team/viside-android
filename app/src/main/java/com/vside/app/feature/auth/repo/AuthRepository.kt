package com.vside.app.feature.auth.repo

import com.vside.app.feature.auth.data.request.NicknameDuplicationCheckRequest
import com.vside.app.feature.auth.data.request.SignInRequest
import com.vside.app.feature.auth.data.request.SignUpRequest
import com.vside.app.feature.auth.service.AuthService
import com.vside.app.util.common.flowApiResponse

class AuthRepository(private val authService: AuthService) {
    suspend fun signUp(signUpRequest: SignUpRequest) = flowApiResponse(authService.signUp(signUpRequest))

    suspend fun signIn(signInRequest: SignInRequest) = flowApiResponse(authService.signIn(signInRequest))

    suspend fun nicknameDuplicationCheck(nicknameDuplicationCheckRequest: NicknameDuplicationCheckRequest) = flowApiResponse(authService.nicknameDuplicationCheck(nicknameDuplicationCheckRequest))
}