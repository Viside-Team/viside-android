package com.vside.app.feature.home.service

import com.skydoves.sandwich.ApiResponse
import com.vside.app.feature.home.data.response.ContentListResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface HomeService {
    @GET("/homelist")
    suspend fun getHomeContentList(@Header("Authorization") jwtAccessToken: String): ApiResponse<ContentListResponse>
}