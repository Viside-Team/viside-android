package com.depayse.data.repository

import com.depayse.data.remote.service.CommonService
import com.depayse.data.remote.model.request.FilteredContentRequest
import com.depayse.data.remote.service.FilterService
import com.depayse.domain.repository.FilterRepository
import java.math.BigInteger

class FilterRepositoryImpl(private val filterService: FilterService, private val commonService: CommonService) : FilterRepository {
    override suspend fun getKeywordsGroupedByCategory(jwtAccessToken: String) = filterService.getKeywordsGroupedByCategory(jwtAccessToken)

    override suspend fun getFilteredContentList(jwtAccessToken: String, keywords: List<String>) = filterService.getFilteredContentList(jwtAccessToken, FilteredContentRequest(keywords))

    override suspend fun toggleContentScrap(jwtAccessToken: String, contentId: BigInteger) = commonService.toggleContentScrap(jwtAccessToken, contentId)
}