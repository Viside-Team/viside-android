package com.vside.app.feature.mypage.service

import com.skydoves.sandwich.ApiResponse
import com.vside.app.feature.mypage.data.response.ProfileResponse
import com.vside.app.feature.mypage.data.response.ScrapListResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface MyPageService {
    @GET("/profile")
    suspend fun getProfile(@Header("Authorization") jwtAccessToken : String) : ApiResponse<ProfileResponse>

    @GET("/scrap/contents")
    suspend fun getScrapList(@Header("Authorization") jwtAccessToken: String) : ApiResponse<ScrapListResponse>
}