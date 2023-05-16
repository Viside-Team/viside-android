package com.vside.app.util.common

import com.depayse.domain.entity.Content
import com.vside.app.feature.filter.data.CategoryKeywordItem

interface ContentItemClickListener {
    fun onContentItemClickListener(item: Content)
    fun onContentItemBookmarkClickListener(item: Content) {}
}

interface KeywordItemClickListener {
    fun onKeywordItemClickListener(item: CategoryKeywordItem.KeywordItem)
}