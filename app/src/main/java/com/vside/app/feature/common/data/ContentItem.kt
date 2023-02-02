package com.vside.app.feature.common.data

import androidx.lifecycle.MutableLiveData
import java.math.BigInteger

data class ContentItem(
    val contentId: BigInteger?,
    val title: String?,
    val coverImgUrl: String?,
    val contentImgUrl: String?,
    val mainKeyword: String?,
    val darkerColor: String?,
    val lighterColor: String?,
    val contentUrl: String?,
    val isBookmark: MutableLiveData<Boolean>,
    val isLightBg: Boolean?,
    val keywords: List<String>?,
    val isScrapClickable: MutableLiveData<Boolean> = MutableLiveData(true)
) {
    constructor(content: Content): this(
        content.contentId,
        content.title,
        content.coverImgUrl,
        content.contentImgUrl,
        content.mainKeyword,
        content.darkerColor,
        content.lighterColor,
        content.contentUrl,
        MutableLiveData(content.isBookmark),
        content.isLightBg,
        content.keywords
    )

    constructor() : this(
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        MutableLiveData(),
        null,
        null
    )
}
