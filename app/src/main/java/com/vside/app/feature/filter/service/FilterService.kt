package com.vside.app.feature.filter.service

import com.skydoves.sandwich.ApiResponse
import com.vside.app.feature.filter.data.request.FilteredContentRequest
import com.vside.app.feature.filter.data.response.FilteredContentResponse
import com.vside.app.feature.filter.data.response.KeywordsGroupedByCategoryResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface FilterService {
    @GET("/keywords")
    fun getKeywordsGroupedByCategory(@Header("Authorization") jwtAccessToken: String): ApiResponse<KeywordsGroupedByCategoryResponse>

    @POST("/search")
    fun getFilteredContentList(@Header("Authorization") jwtAccessToken: String, @Body filteredContentRequest: FilteredContentRequest): ApiResponse<FilteredContentResponse>
}