package com.vside.app.util.mypage

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vside.app.R

@BindingAdapter("contentBackBtnLightBg", "contentBackBtnCollapsed")
fun ImageView.setContentBackBtnStyle(isLightBg: Boolean?, isCollapsed: Boolean?) {
    if(isLightBg == true) {
        setColorFilter(ContextCompat.getColor(context, R.color.g950))
        return
    }

    if(isCollapsed == true) {
        setColorFilter(ContextCompat.getColor(context, R.color.g950))
        return
    }

    setColorFilter(ContextCompat.getColor(context, R.color.white))
}

@BindingAdapter("contentBookmarkLightBg", "contentBookmarkCollapsed", "contentBookmarkActivated")
fun ImageView.setContentBookmarkBtnStyle(isLightBg: Boolean?, isCollapsed: Boolean?, isActivated: Boolean?) {
    if(isActivated == true) {
        setImageResource(R.drawable.ic_bookmark_on)
        if(isLightBg == true) {
            setColorFilter(ContextCompat.getColor(context, R.color.main500))
            return
        }
        if(isCollapsed == true) {
            setColorFilter(ContextCompat.getColor(context, R.color.main500))
            return
        }
        setColorFilter(ContextCompat.getColor(context, R.color.white))
    }
    else {
        setImageResource(R.drawable.ic_bookmark_off)
        if(isLightBg == true) {
            setColorFilter(ContextCompat.getColor(context, R.color.g950))
            return
        }
        if(isCollapsed == true) {
            setColorFilter(ContextCompat.getColor(context, R.color.g950))
            return
        }
        setColorFilter(ContextCompat.getColor(context, R.color.white))
    }
}

@BindingAdapter("contentTitleDateLightBg")
fun TextView.setContentTitleDateStyle(isLightBg: Boolean?) {
    if(isLightBg == true) {
        setTextColor(ContextCompat.getColor(this.context, R.color.g950))
        return
    }

    setTextColor(ContextCompat.getColor(this.context, R.color.white))
}

@BindingAdapter("scrapLayoutManager")
fun RecyclerView.setScrapLayoutManager(spanCnt : Int) {
    layoutManager = GridLayoutManager(context, spanCnt, GridLayoutManager.HORIZONTAL, false)
}