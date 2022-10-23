package com.vside.app.util.common

import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter

@BindingAdapter("bgColorAlpha")
fun View.setBgColorAlpha(alpha0To100: Int?) {
    alpha0To100?.let {
        if(background is ColorDrawable) {
            (background as ColorDrawable).alpha = alpha0To100*255/100
        }
    }
}

@BindingAdapter("cardBgColorAlpha")
fun CardView.setCardBgColorAlpha(alpha0To100: Int?) {
    alpha0To100?.let {
        setCardBackgroundColor(cardBackgroundColor.withAlpha(alpha0To100*255/100))
    }
}