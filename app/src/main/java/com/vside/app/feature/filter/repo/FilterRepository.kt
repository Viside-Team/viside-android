package com.vside.app.feature.filter.repo

import com.vside.app.feature.common.service.CommonService
import com.vside.app.feature.filter.data.request.FilteredContentRequest
import com.vside.app.feature.filter.service.FilterService
import java.math.BigInteger

class FilterRepository(private val filterService: FilterService, private val commonService: CommonService) {
    suspend fun getKeywordsGroupedByCategory(jwtAccessToken: String) = filterService.getKeywordsGroupedByCategory(jwtAccessToken)

    suspend fun getFilteredContentList(jwtAccessToken: String, filteredContentRequest: FilteredContentRequest) = filterService.getFilteredContentList(jwtAccessToken, filteredContentRequest)

    suspend fun toggleContentScrap(jwtAccessToken: String, contentId: BigInteger) = commonService.toggleContentScrap(jwtAccessToken, contentId)
}