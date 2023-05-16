package com.depayse.data.remote.service

import com.depayse.data.remote.model.response.FilteredContentResponse
import com.depayse.data.remote.model.response.KeywordsGroupedByCategoryResponse
import com.depayse.data.remote.model.request.FilteredContentRequest
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface FilterService {
    @GET("/keywords")
    suspend fun getKeywordsGroupedByCategory(@Header("Authorization") jwtAccessToken: String): ApiResponse<KeywordsGroupedByCategoryResponse>

    @POST("/search")
    suspend fun getFilteredContentList(@Header("Authorization") jwtAccessToken: String, @Body filteredContentRequest: FilteredContentRequest): ApiResponse<FilteredContentResponse>
}