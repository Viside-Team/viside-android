package com.vside.app.util.home

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.vside.app.util.common.rgbHexToRgbaPoundHex

@BindingAdapter("colorFilterBottomColor", "colorFilterTopColor")
fun ImageView.setForegroundColorFilter(colorFilterBottomColor: String?, colorFilterTopColor: String?) {
    val startColorInt =
        if(colorFilterBottomColor.isNullOrEmpty()) Color.TRANSPARENT
        else Color.parseColor(rgbHexToRgbaPoundHex(colorFilterBottomColor, 0.9f))
    val endColorInt =
        if(colorFilterTopColor.isNullOrEmpty()) Color.TRANSPARENT
        else Color.parseColor(rgbHexToRgbaPoundHex(colorFilterTopColor, 0.2f))
    val gradientDrawable = GradientDrawable(
        GradientDrawable.Orientation.BOTTOM_TOP,
        intArrayOf(startColorInt, endColorInt, Color.TRANSPARENT)
    )
    foreground = gradientDrawable
}