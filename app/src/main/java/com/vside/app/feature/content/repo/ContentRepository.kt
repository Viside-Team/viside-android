package com.vside.app.feature.content.repo

import com.vside.app.feature.common.service.CommonService
import com.vside.app.feature.content.service.ContentService
import com.vside.app.util.common.flowApiResponse
import java.math.BigInteger

class ContentRepository(private val contentService: ContentService, private val commonService: CommonService) {
    suspend fun getContentDetail(tokenBearer: String, contentId: BigInteger) = flowApiResponse(contentService.getContentDetail(tokenBearer, contentId))

    suspend fun toggleContentScrap(tokenBearer: String, contentId: BigInteger) = flowApiResponse(commonService.toggleContentScrap(tokenBearer, contentId))
}