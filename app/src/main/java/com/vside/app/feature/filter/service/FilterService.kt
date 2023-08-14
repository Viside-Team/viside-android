package com.vside.app.feature.filter.service

import com.skydoves.sandwich.ApiResponse
import com.vside.app.feature.filter.data.request.FilteredContentRequest
import com.vside.app.feature.filter.data.response.FilteredContentResponse
import com.vside.app.feature.filter.data.response.KeywordsGroupedByCategoryResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FilterService {
    @GET("/keywords")
    suspend fun getKeywordsGroupedByCategory(): ApiResponse<KeywordsGroupedByCategoryResponse>

    @POST("/search")
    suspend fun getFilteredContentList(@Body filteredContentRequest: FilteredContentRequest): ApiResponse<FilteredContentResponse>
}