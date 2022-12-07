package com.vside.app.feature.common.service

import com.skydoves.sandwich.ApiResponse
import com.vside.app.feature.mypage.data.response.ProfileResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface CommonService {
    @GET("/profile")
    suspend fun getProfile(@Header("Authorization") jwtAccessToken : String) : ApiResponse<ProfileResponse>
}