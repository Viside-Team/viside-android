package com.vside.app.feature.content.service

import com.skydoves.sandwich.ApiResponse
import com.vside.app.feature.common.data.Content
import com.vside.app.feature.common.data.response.BasicResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import java.math.BigInteger

interface ContentService {
    @GET("/contents/{contentId}")
    suspend fun getContentDetail(@Header("Authorization") jwtAccessToken: String, @Path("contentId") contentId: BigInteger): ApiResponse<Content>

    @POST("/scrap/{contentId}")
    suspend fun toggleScrapContent(@Header("Authorization") jwtAccessToken: String): ApiResponse<BasicResponse>
}