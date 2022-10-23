package com.vside.app.util

import android.content.Context
import android.util.TypedValue

/**
 * dp 를 px 로 바꿔주는 함수
 */
fun dpToPx(context: Context, dp: Float): Float {
    val dm = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm)
}

/**
 * px 을 dp 로 바꿔주는 함수
 */
fun pxToDp(context: Context, px: Float): Float {
    var density = context.resources.displayMetrics.density

    when (density) {
        1.0f -> density *= 4.0f
        1.5f -> density *= (8 / 3)
        2.0f -> density *= 2.0f
    }

    return px / density
}
