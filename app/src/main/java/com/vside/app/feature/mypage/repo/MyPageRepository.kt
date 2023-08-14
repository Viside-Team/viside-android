package com.vside.app.feature.mypage.repo

import com.vside.app.feature.auth.data.request.WithdrawRequest
import com.vside.app.feature.auth.service.AuthService
import com.vside.app.feature.common.service.CommonService
import com.vside.app.feature.mypage.service.MyPageService
import java.math.BigInteger

class MyPageRepository(private val myPageService: MyPageService, private val authService: AuthService, private val commonService: CommonService) {
    suspend fun getScrapList() = myPageService.getScrapList()

    suspend fun signOut() = authService.signOut()

    suspend fun withdraw(withdrawRequest: WithdrawRequest) = authService.withdraw(withdrawRequest)

    suspend fun getProfile() = commonService.getProfile()

    suspend fun toggleContentScrap(contentId: BigInteger) = commonService.toggleContentScrap(contentId)
}