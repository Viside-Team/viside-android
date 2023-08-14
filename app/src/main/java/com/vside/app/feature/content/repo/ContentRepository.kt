package com.vside.app.feature.content.repo

import com.vside.app.feature.common.service.CommonService
import com.vside.app.feature.content.service.ContentService
import java.math.BigInteger

class ContentRepository(private val contentService: ContentService, private val commonService: CommonService) {
    suspend fun getContentDetail(contentId: BigInteger) = contentService.getContentDetail(contentId)

    suspend fun toggleContentScrap(contentId: BigInteger) = commonService.toggleContentScrap(contentId)
}