package com.vside.app.feature.filter.repo

import com.vside.app.feature.common.service.CommonService
import com.vside.app.feature.filter.data.request.FilteredContentRequest
import com.vside.app.feature.filter.service.FilterService
import java.math.BigInteger

class FilterRepository(private val filterService: FilterService, private val commonService: CommonService) {
    suspend fun getKeywordsGroupedByCategory() = filterService.getKeywordsGroupedByCategory()

    suspend fun getFilteredContentList(filteredContentRequest: FilteredContentRequest) = filterService.getFilteredContentList(filteredContentRequest)

    suspend fun toggleContentScrap(contentId: BigInteger) = commonService.toggleContentScrap(contentId)
}