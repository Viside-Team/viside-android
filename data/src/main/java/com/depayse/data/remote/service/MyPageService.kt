package com.depayse.data.remote.service

import com.depayse.data.remote.model.response.ScrapListResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface MyPageService {
    @GET("/scrap/contents")
    suspend fun getScrapList(@Header("Authorization") jwtAccessToken: String) : ApiResponse<ScrapListResponse>
}