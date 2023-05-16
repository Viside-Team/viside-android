package com.vside.app.feature.filter.repo

import com.depayse.data.remote.service.CommonService
import com.depayse.data.remote.model.request.FilteredContentRequest
import com.depayse.data.remote.service.FilterService
import java.math.BigInteger

class FilterRepository(private val filterService: FilterService, private val commonService: CommonService) {
    suspend fun getKeywordsGroupedByCategory(jwtAccessToken: String) = filterService.getKeywordsGroupedByCategory(jwtAccessToken)

    suspend fun getFilteredContentList(jwtAccessToken: String, keywords: List<String>) = filterService.getFilteredContentList(jwtAccessToken, FilteredContentRequest(keywords))

    suspend fun toggleContentScrap(jwtAccessToken: String, contentId: BigInteger) = commonService.toggleContentScrap(jwtAccessToken, contentId)
}