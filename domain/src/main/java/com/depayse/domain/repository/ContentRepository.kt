package com.depayse.domain.repository

import com.depayse.domain.entity.Content
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.flow.Flow
import java.math.BigInteger

interface ContentRepository {

    suspend fun getContentDetail(tokenBearer: String, contentId: BigInteger) : Flow<ApiResponse<Content>>

    suspend fun toggleContentScrap(tokenBearer: String, contentId: BigInteger)
}