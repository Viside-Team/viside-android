package com.vside.app.feature.auth.service

import com.skydoves.sandwich.ApiResponse
import com.vside.app.feature.auth.data.request.SignInRequest
import com.vside.app.feature.auth.data.request.SignUpRequest
import com.vside.app.feature.auth.data.response.SignInResponse
import com.vside.app.feature.auth.data.response.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/signin")
    suspend fun signUp(@Body signUpRequest: SignUpRequest) : ApiResponse<SignUpResponse>

    @POST("/login")
    suspend fun signIn(@Body signInRequest: SignInRequest) : ApiResponse<SignInResponse>
}