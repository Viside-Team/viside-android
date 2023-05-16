package com.depayse.data.remote.mapper

import com.depayse.data.remote.model.ContentDTO
import com.depayse.domain.entity.Content
import kotlinx.coroutines.flow.MutableStateFlow

fun ContentDTO.toDomain() = Content(
    contentId,
    title,
    dateStr,
    coverImgUrl,
    contentImgUrl,
    mainKeyword,
    darkerColor,
    lighterColor,
    contentUrl,
    isBookmark ?: false,
    isLightBg,
    keywords
)

fun Content.toData() = ContentDTO(
    contentId,
    title,
    dateStr,
    coverImgUrl,
    contentImgUrl,
    mainKeyword,
    darkerColor,
    lighterColor,
    contentUrl,
    isBookmark,
    isLightBg,
    keywords
)