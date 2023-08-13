package com.vside.app.feature.content.repo

import com.depayse.data.remote.service.CommonService
import com.depayse.data.remote.service.ContentService
import java.math.BigInteger

class ContentRepository(private val contentService: ContentService, private val commonService: CommonService) {
    suspend fun getContentDetail(tokenBearer: String, contentId: BigInteger) = contentService.getContentDetail(tokenBearer, contentId)

    suspend fun toggleContentScrap(tokenBearer: String, contentId: BigInteger) = commonService.toggleContentScrap(tokenBearer, contentId)
}