package com.depayse.domain.entity

import java.math.BigInteger


data class Content(
    val contentId: BigInteger? = null,
    val title: String? = null,
    val dateStr: String? = null,
    val coverImgUrl: String? = null,
    val contentImgUrl: String? = null,
    val mainKeyword: String? = null,
    val darkerColor: String? = null,
    val lighterColor: String? = null,
    val contentUrl: String? = null,
    val isBookmark: Boolean = false,
    val isLightBg: Boolean? = null,
    val keywords: List<String>? = null,
    val isScrapClickable: Boolean = true
)
