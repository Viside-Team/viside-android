package com.vside.app.util.common

import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.RawRes
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

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
        val temp = ((alpha0To100 / 100f) * 256).toInt()
        val alpha = if(temp > 255) 255 else temp
        setCardBackgroundColor(cardBackgroundColor.withAlpha(alpha))
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

@BindingAdapter("setFlexboxLayoutManager")
fun RecyclerView.setFlexboxLayoutManager(boolean: Boolean?) {
    boolean?.let {
        if(boolean) {
            FlexboxLayoutManager(context).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
                justifyContent = JustifyContent.FLEX_START
            }.let {
                layoutManager = it
            }
        }
    }
}