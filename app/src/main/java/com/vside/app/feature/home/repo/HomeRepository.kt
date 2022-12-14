package com.vside.app.feature.home.repo

import com.vside.app.feature.home.service.HomeService
import com.vside.app.util.common.flowApiResponse

class HomeRepository(private val homeService: HomeService) {
    suspend fun getHomeContentList(jwtAccessToken: String) = flowApiResponse(homeService.getHomeContentList(jwtAccessToken))
}