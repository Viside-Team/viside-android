package com.vside.app.feature.common

import com.vside.app.feature.common.data.Content

interface HomeContentItemClickListener {
    fun onHomeContentClick(item: Content)
    fun onHomeContentBookmarkClick(item: Content)
}