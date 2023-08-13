package com.depayse.data.remote.service

import com.depayse.data.remote.model.ContentDTO
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import java.math.BigInteger

interface ContentService {
    @GET("/contents/{contentId}")
    suspend fun getContentDetail(@Header("Authorization") jwtAccessToken: String, @Path("contentId") contentId: BigInteger): ApiResponse<ContentDTO>
}