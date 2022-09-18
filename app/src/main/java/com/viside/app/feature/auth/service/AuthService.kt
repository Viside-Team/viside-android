package com.viside.app.feature.auth.service

import com.skydoves.sandwich.ApiResponse
import com.viside.app.feature.auth.data.request.SignInRequest
import com.viside.app.feature.auth.data.request.SignUpRequest
import com.viside.app.feature.auth.data.response.SignInResponse
import com.viside.app.feature.auth.data.response.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/signin")
    suspend fun signUp(@Body signUpRequest: SignUpRequest) : ApiResponse<SignUpResponse>

    @POST("/login")
    suspend fun signIn(@Body signInRequest: SignInRequest) : ApiResponse<SignInResponse>
}