package com.vside.app.feature.content.repo

import com.vside.app.feature.content.service.ContentService
import com.vside.app.util.common.flowApiResponse
import java.math.BigInteger

class ContentRepository(private val contentService: ContentService) {
    suspend fun getContentDetail(tokenBearer: String, contentId: BigInteger) = flowApiResponse(contentService.getContentDetail(tokenBearer, contentId))

    suspend fun toggleScrapContent(tokenBearer: String, contentId: BigInteger) = flowApiResponse(contentService.getContentDetail(tokenBearer, contentId))
}