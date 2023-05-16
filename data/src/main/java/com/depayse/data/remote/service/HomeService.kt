package com.depayse.data.remote.service

import com.depayse.data.remote.model.response.ContentListResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface HomeService {
    @GET("/homelist")
    suspend fun getHomeContentList(@Header("Authorization") jwtAccessToken: String): ApiResponse<ContentListResponse>
}