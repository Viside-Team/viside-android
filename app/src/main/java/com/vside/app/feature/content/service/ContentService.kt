package com.vside.app.feature.content.service

import com.skydoves.sandwich.ApiResponse
import com.vside.app.feature.common.data.Content
import retrofit2.http.GET
import retrofit2.http.Path
import java.math.BigInteger

interface ContentService {
    @GET("/contents/{contentId}")
    suspend fun getContentDetail(@Path("contentId") contentId: BigInteger): ApiResponse<Content>
}