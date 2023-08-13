package com.depayse.data.repository

import com.depayse.data.remote.service.CommonService
import com.depayse.data.remote.service.HomeService
import com.depayse.domain.repository.HomeRepository
import java.math.BigInteger

class HomeRepositoryImpl(private val homeService: HomeService, private val commonService: CommonService) : HomeRepository {
    override suspend fun getHomeContentList(jwtAccessToken: String) = homeService.getHomeContentList(jwtAccessToken)

    override suspend fun getProfile(jwtAccessToken: String) = commonService.getProfile(jwtAccessToken)

    override suspend fun toggleContentScrap(jwtAccessToken: String, contentId: BigInteger) = commonService.toggleContentScrap(jwtAccessToken, contentId)
}