package com.depayse.domain.repository

import java.math.BigInteger

interface MyPageRepository {
    suspend fun getScrapList(tokenBearer: String)

    suspend fun signOut(tokenBearer: String)

    suspend fun withdraw(tokenBearer: String, snsId: String)

    suspend fun getProfile(tokenBearer: String)

    suspend fun toggleContentScrap(jwtAccessToken: String, contentId: BigInteger)
}