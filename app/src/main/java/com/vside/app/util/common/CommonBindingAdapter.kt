package com.vside.app.util.common

import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

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

@BindingAdapter("imgUrl")
fun ImageView.setImageByUrl(url: String?) {
    url?.let {
        Glide.with(context)
            .load(it)
            .into(this)
    }
}

@BindingAdapter("gifRaw")
fun ImageView.setGifDrawable(@RawRes rawResId: Int) {
    Glide.with(context).load(rawResId).into(this)
}

@BindingAdapter("activated")
fun View.setActivation(activate: Boolean?) {
    activate?.let {
        isActivated = it
    }
}