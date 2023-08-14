package com.vside.app.feature.auth.service

import com.skydoves.sandwich.ApiResponse
import com.vside.app.feature.auth.data.request.NicknameDuplicationCheckRequest
import com.vside.app.feature.auth.data.request.SignInRequest
import com.vside.app.feature.auth.data.request.SignUpRequest
import com.vside.app.feature.auth.data.request.WithdrawRequest
import com.vside.app.feature.auth.data.response.NicknameDuplicationCheckResponse
import com.vside.app.feature.auth.data.response.SignInResponse
import com.vside.app.feature.auth.data.response.SignUpResponse
import com.vside.app.feature.common.data.response.BasicMessageResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

interface AuthService {
    @POST("/signin")
    suspend fun signUp(@Body signUpRequest: SignUpRequest) : ApiResponse<SignUpResponse>

    @POST("/login")
    suspend fun signIn(@Body signInRequest: SignInRequest) : ApiResponse<SignInResponse>

    @DELETE("/logout1")
    suspend fun signOut(): ApiResponse<BasicMessageResponse>

    @POST("/withdrawal")
    suspend fun withdraw(@Body withdrawRequest: WithdrawRequest): ApiResponse<BasicMessageResponse>

    @POST("/nameCheck")
    suspend fun nicknameDuplicationCheck(@Body nicknameDuplicationCheckRequest: NicknameDuplicationCheckRequest) : ApiResponse<NicknameDuplicationCheckResponse>
}