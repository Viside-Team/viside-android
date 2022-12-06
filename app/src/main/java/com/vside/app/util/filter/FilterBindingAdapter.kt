package com.vside.app.util.filter

import android.graphics.Color
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView
import com.vside.app.R

@BindingAdapter("keywordCardViewActivated")
fun MaterialCardView.setCardViewStyle(isActivated: Boolean?) {
    if(isActivated == true) {
        setCardBackgroundColor(ContextCompat.getColor(context, R.color.main100))
        strokeColor = ContextCompat.getColor(context, R.color.main500)
    }

    setCardBackgroundColor(ContextCompat.getColor(context, R.color.g50))
    strokeColor = Color.TRANSPARENT
}

@BindingAdapter("keywordTextActivated")
fun TextView.setKeywordTextStyle(isActivated: Boolean?) {
    if(isActivated == true) {
        setTextAppearance(R.style.TextAppearance_AppCompat_MD_M)
        setTextColor(ContextCompat.getColor(context, R.color.main500))
        return
    }

    setTextAppearance(R.style.TextAppearance_AppCompat_MD_R)
    setTextColor(ContextCompat.getColor(context, R.color.g600))
}