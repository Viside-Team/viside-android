package com.vside.app.feature.filter.repo

import com.vside.app.feature.filter.data.request.FilteredContentRequest
import com.vside.app.feature.filter.service.FilterService
import com.vside.app.util.common.flowApiResponse

class FilterRepository(private val filterService: FilterService) {
    suspend fun getKeywordsGroupedByCategory(jwtAccessToken: String) = flowApiResponse(filterService.getKeywordsGroupedByCategory(jwtAccessToken))

    suspend fun getFilteredContentList(jwtAccessToken: String, filteredContentRequest: FilteredContentRequest) = flowApiResponse(filterService.getFilteredContentList(jwtAccessToken, filteredContentRequest))
}