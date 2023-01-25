package com.vside.app.util.common

import com.vside.app.feature.common.data.ContentItem
import com.vside.app.feature.filter.data.CategoryKeywordItem

interface ContentItemClickListener {
    fun onContentItemClickListener(item: ContentItem)
    fun onContentItemBookmarkClickListener(item: ContentItem) {}
}

interface KeywordItemClickListener {
    fun onKeywordItemClickListener(item: CategoryKeywordItem.KeywordItem)
}