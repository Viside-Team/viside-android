package com.vside.app.feature.mypage.repo

import com.vside.app.feature.auth.data.request.WithdrawRequest
import com.vside.app.feature.auth.service.AuthService
import com.vside.app.feature.mypage.service.MyPageService
import com.vside.app.util.common.flowApiResponse

class MyPageRepository(private val myPageService: MyPageService, private val authService: AuthService) {
    suspend fun getScrapList(jwtAccessToken: String) = flowApiResponse(myPageService.getScrapList(jwtAccessToken))

    suspend fun signOut(jwtAccessToken: String) = flowApiResponse(authService.signOut(jwtAccessToken))

    suspend fun withdraw(jwtAccessToken: String, withdrawRequest: WithdrawRequest) = flowApiResponse(authService.withdraw(jwtAccessToken, withdrawRequest))
}