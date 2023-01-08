package com.vside.app.util.common

import com.vside.app.feature.common.data.ContentItem

interface HomeContentItemClickListener {
    fun onHomeContentItemClickListener(item: ContentItem)
    fun onHomeContentItemBookmarkClickListener(item: ContentItem)
}