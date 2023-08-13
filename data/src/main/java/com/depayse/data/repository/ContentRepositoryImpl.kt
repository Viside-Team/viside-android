package com.depayse.data.repository

import com.depayse.data.remote.mapper.toDomain
import com.depayse.data.remote.service.CommonService
import com.depayse.data.remote.service.ContentService
import com.depayse.domain.repository.ContentRepository
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.suspendOperator
import com.skydoves.sandwich.toFlow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.math.BigInteger

class ContentRepositoryImpl(private val contentService: ContentService, private val commonService: CommonService) : ContentRepository {
    override suspend fun getContentDetail(tokenBearer: String, contentId: BigInteger) =
        contentService.getContentDetail(tokenBearer, contentId).collect {
            it.suspendOperator()
        }

    override suspend fun toggleContentScrap(tokenBearer: String, contentId: BigInteger) = commonService.toggleContentScrap(tokenBearer, contentId)
}