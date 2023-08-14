package com.vside.app.feature.home.repo

import com.vside.app.feature.common.service.CommonService
import com.vside.app.feature.home.service.HomeService
import java.math.BigInteger

class HomeRepository(private val homeService: HomeService, private val commonService: CommonService) {
    suspend fun getHomeContentList() = homeService.getHomeContentList()

    suspend fun getProfile() = commonService.getProfile()

    suspend fun toggleContentScrap(contentId: BigInteger) = commonService.toggleContentScrap(contentId)
}