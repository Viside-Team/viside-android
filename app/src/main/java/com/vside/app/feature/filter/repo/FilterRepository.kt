package com.vside.app.feature.filter.repo

import com.vside.app.feature.common.service.CommonService
import com.vside.app.feature.filter.data.request.FilteredContentRequest
import com.vside.app.feature.filter.service.FilterService
import com.vside.app.util.common.flowApiResponse
import java.math.BigInteger

class FilterRepository(private val filterService: FilterService, private val commonService: CommonService) {
    suspend fun getKeywordsGroupedByCategory(jwtAccessToken: String) = flowApiResponse(filterService.getKeywordsGroupedByCategory(jwtAccessToken))

    suspend fun getFilteredContentList(jwtAccessToken: String, filteredContentRequest: FilteredContentRequest) = flowApiResponse(filterService.getFilteredContentList(jwtAccessToken, filteredContentRequest))

    suspend fun toggleContentScrap(jwtAccessToken: String, contentId: BigInteger) = flowApiResponse(commonService.toggleContentScrap(jwtAccessToken, contentId))
}