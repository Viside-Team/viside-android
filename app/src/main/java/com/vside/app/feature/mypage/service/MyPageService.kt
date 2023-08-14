package com.vside.app.feature.mypage.service

import com.skydoves.sandwich.ApiResponse
import com.vside.app.feature.mypage.data.response.ScrapListResponse
import retrofit2.http.GET

interface MyPageService {
    @GET("/scrap/contents")
    suspend fun getScrapList() : ApiResponse<ScrapListResponse>
}