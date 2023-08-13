package com.vside.app.feature.mypage.repo

import com.depayse.data.remote.model.request.WithdrawRequest
import com.depayse.data.remote.service.AuthService
import com.depayse.data.remote.service.CommonService
import com.depayse.data.remote.service.MyPageService
import java.math.BigInteger

class MyPageRepository(private val myPageService: MyPageService, private val authService: AuthService, private val commonService: CommonService) {
    suspend fun getScrapList(tokenBearer: String) = myPageService.getScrapList(tokenBearer)

    suspend fun signOut(tokenBearer: String) = authService.signOut(tokenBearer)

    suspend fun withdraw(tokenBearer: String, snsId: String) = authService.withdraw(tokenBearer, WithdrawRequest(snsId))

    suspend fun getProfile(tokenBearer: String) = commonService.getProfile(tokenBearer)

    suspend fun toggleContentScrap(jwtAccessToken: String, contentId: BigInteger) = commonService.toggleContentScrap(jwtAccessToken, contentId)
}