package com.vside.app.util.common

import com.vside.app.feature.common.data.ContentItem

interface ContentItemClickListener {
    fun onContentItemClickListener(item: ContentItem)
    fun onContentItemBookmarkClickListener(item: ContentItem) {}
}