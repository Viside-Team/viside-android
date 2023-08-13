package com.vside.app.feature.common.service

import com.skydoves.sandwich.ApiResponse
import com.vside.app.feature.common.data.response.BasicResponse
import com.vside.app.feature.mypage.data.response.ProfileResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import java.math.BigInteger

interface CommonService {
    @GET("/profile")
    suspend fun getProfile(@Header("Authorization") jwtAccessToken : String) : ApiResponse<ProfileResponse>

    @POST("/scrap/{contentId}")
    suspend fun toggleContentScrap(@Header("Authorization") jwtAccessToken: String, @Path("contentId") contentId: BigInteger): ApiResponse<BasicResponse>
}