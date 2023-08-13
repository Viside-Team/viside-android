package com.depayse.domain.repository

import java.math.BigInteger

interface FilterRepository {

    suspend fun getKeywordsGroupedByCategory(jwtAccessToken: String)

    suspend fun getFilteredContentList(jwtAccessToken: String, keywords: List<String>)

    suspend fun toggleContentScrap(jwtAccessToken: String, contentId: BigInteger)
}