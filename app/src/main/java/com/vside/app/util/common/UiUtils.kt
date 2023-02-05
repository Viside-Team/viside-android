package com.vside.app.util.common

import android.content.Context
import okhttp3.internal.toHexString

fun statusBarHeight(context: Context): Int {
    val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")

    return if (resourceId > 0) context.resources.getDimensionPixelSize(resourceId)
    else 0
}

fun navigationHeight(context: Context): Int {
    val resourceId = context.resources.getIdentifier("navigation_bar_height", "dimen", "android")

    return if (resourceId > 0) context.resources.getDimensionPixelSize(resourceId)
    else 0
}

fun rgbHexToRgbaPoundHex(rgbHex: String, alpha: Float) = "#${(256 * alpha).toInt().toHexString()}${rgbHex.trim('#')}"
