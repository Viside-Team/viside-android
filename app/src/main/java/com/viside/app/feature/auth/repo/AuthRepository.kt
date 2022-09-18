package com.viside.app.feature.auth.repo

import com.viside.app.feature.auth.data.request.SignInRequest
import com.viside.app.feature.auth.data.request.SignUpRequest
import com.viside.app.feature.auth.service.AuthService
import com.viside.app.util.common.flowApiResponse

class AuthRepository(private val authService: AuthService) {
    suspend fun signUp(signUpRequest: SignUpRequest) = flowApiResponse(authService.signUp(signUpRequest))

    suspend fun signIn(signInRequest: SignInRequest) = flowApiResponse(authService.signIn(signInRequest))
}