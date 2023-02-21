package com.vside.app.feature.mypage.repo

import com.vside.app.feature.auth.data.request.WithdrawRequest
import com.vside.app.feature.auth.service.AuthService
import com.vside.app.feature.common.service.CommonService
import com.vside.app.feature.mypage.service.MyPageService
import java.math.BigInteger

class MyPageRepository(private val myPageService: MyPageService, private val authService: AuthService, private val commonService: CommonService) {
    suspend fun getScrapList(tokenBearer: String) = myPageService.getScrapList(tokenBearer)

    suspend fun signOut(tokenBearer: String) = authService.signOut(tokenBearer)

    suspend fun withdraw(tokenBearer: String, withdrawRequest: WithdrawRequest) = authService.withdraw(tokenBearer, withdrawRequest)

    suspend fun getProfile(tokenBearer: String) = commonService.getProfile(tokenBearer)

    suspend fun toggleContentScrap(jwtAccessToken: String, contentId: BigInteger) = commonService.toggleContentScrap(jwtAccessToken, contentId)
}