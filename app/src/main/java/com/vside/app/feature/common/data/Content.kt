package com.vside.app.feature.common.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.math.BigInteger

@Parcelize
data class Content(
    @SerializedName("contentId") val contentId: BigInteger?,
    @SerializedName("title") val title: String?,
    @SerializedName("coverImgUrl") val coverImgUrl: String?,
    @SerializedName("contentImgUrl") val contentImgUrl: String?,
    @SerializedName("mainKeyword") val mainKeyword: String?,
    @SerializedName("darkerColor") val darkerColor: String?,
    @SerializedName("lighterColor") val lighterColor: String?,
    @SerializedName("contentLink") val contentUrl: String?,
    @SerializedName("scrap") val isBookmark: Boolean?,
    @SerializedName("isLightBg") val isLightBg: Boolean?,
    @SerializedName("keywords") val keywords: List<String>?
): Parcelable {
    constructor(contentItem: ContentItem) : this(
        contentItem.contentId,
        contentItem.title,
        contentItem.coverImgUrl,
        contentItem.contentImgUrl,
        contentItem.mainKeyword,
        contentItem.darkerColor,
        contentItem.lighterColor,
        contentItem.contentUrl,
        contentItem.isBookmark.value,
        contentItem.isLightBg,
        contentItem.keywords
    )
}
