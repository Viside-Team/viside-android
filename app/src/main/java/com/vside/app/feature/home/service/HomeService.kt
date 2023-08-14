package com.vside.app.feature.home.service

import com.skydoves.sandwich.ApiResponse
import com.vside.app.feature.home.data.response.ContentListResponse
import retrofit2.http.GET

interface HomeService {
    @GET("/homelist")
    suspend fun getHomeContentList(): ApiResponse<ContentListResponse>
}