package com.depayse.domain.repository

import java.math.BigInteger

interface HomeRepository {
    suspend fun getHomeContentList(jwtAccessToken: String)

    suspend fun getProfile(jwtAccessToken: String)

    suspend fun toggleContentScrap(jwtAccessToken: String, contentId: BigInteger)
}