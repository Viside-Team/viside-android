package com.vside.app.util.home

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("colorFilterBottomColor", "colorFilterTopColor")
fun ImageView.setForegroundColorFilter(colorFilterBottomColor: String?, colorFilterTopColor: String?) {
    val startColorInt = if(colorFilterBottomColor.isNullOrEmpty()) Color.TRANSPARENT else Color.parseColor(colorFilterBottomColor)
    val endColorInt = Color.TRANSPARENT
    val gradientDrawable = GradientDrawable(
        GradientDrawable.Orientation.BOTTOM_TOP,
        intArrayOf(startColorInt, endColorInt, endColorInt)
    )
    foreground = gradientDrawable
}