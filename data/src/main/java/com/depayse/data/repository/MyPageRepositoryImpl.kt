package com.depayse.data.repository

import com.depayse.data.remote.model.request.WithdrawRequest
import com.depayse.data.remote.service.AuthService
import com.depayse.data.remote.service.CommonService
import com.depayse.data.remote.service.MyPageService
import com.depayse.domain.repository.MyPageRepository
import java.math.BigInteger

class MyPageRepositoryImpl(private val myPageService: MyPageService, private val authService: AuthService, private val commonService: CommonService): MyPageRepository {
    override suspend fun getScrapList(tokenBearer: String) = myPageService.getScrapList(tokenBearer)

    override suspend fun signOut(tokenBearer: String) = authService.signOut(tokenBearer)

    override suspend fun withdraw(tokenBearer: String, snsId: String) = authService.withdraw(tokenBearer, WithdrawRequest(snsId))

    override suspend fun getProfile(tokenBearer: String) = commonService.getProfile(tokenBearer)

    override suspend fun toggleContentScrap(jwtAccessToken: String, contentId: BigInteger) = commonService.toggleContentScrap(jwtAccessToken, contentId)
}