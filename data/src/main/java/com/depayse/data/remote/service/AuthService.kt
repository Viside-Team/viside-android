package com.depayse.data.remote.service

import com.depayse.data.remote.model.response.NicknameDuplicationCheckResponse
import com.depayse.data.remote.model.response.SignInResponse
import com.depayse.data.remote.model.response.SignUpResponse
import com.depayse.data.remote.model.response.base.BasicMessageResponse
import com.depayse.data.remote.model.request.NicknameDuplicationCheckRequest
import com.depayse.data.remote.model.request.SignInRequest
import com.depayse.data.remote.model.request.SignUpRequest
import com.depayse.data.remote.model.request.WithdrawRequest
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("/signin")
    suspend fun signUp(@Body signUpRequest: SignUpRequest) : ApiResponse<SignUpResponse>

    @POST("/login")
    suspend fun signIn(@Body signInRequest: SignInRequest) : ApiResponse<SignInResponse>

    @DELETE("/logout1")
    suspend fun signOut(@Header("Authorization") jwtAccessToken : String): ApiResponse<BasicMessageResponse>

    @POST("/withdrawal")
    suspend fun withdraw(@Header("Authorization") jwtAccessToken : String, @Body withdrawRequest: WithdrawRequest): ApiResponse<BasicMessageResponse>

    @POST("/nameCheck")
    suspend fun nicknameDuplicationCheck(@Body nicknameDuplicationCheckRequest: NicknameDuplicationCheckRequest) : ApiResponse<NicknameDuplicationCheckResponse>
}