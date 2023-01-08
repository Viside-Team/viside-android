package com.vside.app.feature.home.repo

import com.vside.app.feature.common.service.CommonService
import com.vside.app.feature.home.service.HomeService
import com.vside.app.util.common.flowApiResponse
import java.math.BigInteger

class HomeRepository(private val homeService: HomeService, private val commonService: CommonService) {
    suspend fun getHomeContentList(jwtAccessToken: String) = flowApiResponse(homeService.getHomeContentList(jwtAccessToken))

    suspend fun getProfile(jwtAccessToken: String) = flowApiResponse(commonService.getProfile(jwtAccessToken))

    suspend fun toggleContentScrap(jwtAccessToken: String, contentId: BigInteger) = flowApiResponse(commonService.toggleContentScrap(jwtAccessToken, contentId))
}