package com.vside.app.feature.mypage.repo

import com.vside.app.feature.auth.data.request.WithdrawRequest
import com.vside.app.feature.auth.service.AuthService
import com.vside.app.feature.common.service.CommonService
import com.vside.app.feature.mypage.service.MyPageService
import com.vside.app.util.common.flowApiResponse
import java.math.BigInteger

class MyPageRepository(private val myPageService: MyPageService, private val authService: AuthService, private val commonService: CommonService) {
    suspend fun getScrapList(tokenBearer: String) = flowApiResponse(myPageService.getScrapList(tokenBearer))

    suspend fun signOut(tokenBearer: String) = flowApiResponse(authService.signOut(tokenBearer))

    suspend fun withdraw(tokenBearer: String, withdrawRequest: WithdrawRequest) = flowApiResponse(authService.withdraw(tokenBearer, withdrawRequest))

    suspend fun getProfile(tokenBearer: String) = flowApiResponse(commonService.getProfile(tokenBearer))

    suspend fun toggleContentScrap(jwtAccessToken: String, contentId: BigInteger) = flowApiResponse(commonService.toggleContentScrap(jwtAccessToken, contentId))
}