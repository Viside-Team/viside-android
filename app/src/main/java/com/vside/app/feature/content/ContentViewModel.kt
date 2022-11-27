package com.vside.app.feature.content

import androidx.lifecycle.MutableLiveData
import com.vside.app.util.base.BaseViewModel

class ContentViewModel: BaseViewModel() {
    val isContentImgCollapsed = MutableLiveData<Boolean>()

    val isLightBg = MutableLiveData<Boolean>()

    val isBookmarked = MutableLiveData<Boolean>()
}