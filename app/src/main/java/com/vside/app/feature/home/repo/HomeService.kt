package com.vside.app.feature.home.repo

import com.skydoves.sandwich.ApiResponse
import com.vside.app.feature.home.data.response.ContentListResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface HomeService {
    @GET("/homelist")
    fun getHomeContentList(@Header("Authorization") jwtAccessToken: String): ApiResponse<ContentListResponse>
}