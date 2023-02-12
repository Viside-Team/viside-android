package com.vside.app.feature.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.vside.app.R
import com.vside.app.databinding.ActivityBookShelfBinding
import com.vside.app.feature.common.data.Content
import com.vside.app.feature.common.data.ContentItem
import com.vside.app.feature.content.ContentActivity
import com.vside.app.util.base.BaseActivity
import com.vside.app.util.common.DataTransfer
import org.koin.android.ext.android.inject
import java.math.BigInteger

class BookShelfActivity : BaseActivity<ActivityBookShelfBinding, BookShelfViewModel>() {
    override val layoutResId: Int = R.layout.activity_book_shelf
    override val viewModel: BookShelfViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.viewModel = viewModel

        initData()
        observeData()
    }

    private fun initData() {
        getScrapList()
    }

    private fun observeData() {
        val appCompatActivity = this@BookShelfActivity
        with(viewModel) {
            isBackClicked.observe(appCompatActivity) {
                onBackPressed()
            }

            isContentItemClicked.observe(appCompatActivity) {
                startActivity(
                    Intent(appCompatActivity, ContentActivity::class.java)
                        .putExtra(DataTransfer.CONTENT, Content(it))
                )
            }

            isContentBookmarkClicked.observe(appCompatActivity) {
                toggleScrapContent(it)
            }
        }
    }

    private fun toggleScrapContent(contentItem: ContentItem) {
        lifecycleScope.launchWhenCreated {
            if(contentItem.isScrapClickable.value == true) {
                contentItem.isScrapClickable.value = false
                val isBookmarked = contentItem.isBookmark.value
                isBookmarked?.let {
                    contentItem.isBookmark.value = !isBookmarked
                }
                viewModel.toggleScrapContent(
                    contentItem.contentId ?: BigInteger("0"),
                    onPostSuccess = {
                        contentItem.isScrapClickable.value = true
                        viewModel.deleteContent(contentItem)
                    },
                    onPostFail = {
                        toastShortOfFailMessage("스크랩 / 스크랩 취소")
                        contentItem.isScrapClickable.value = false
                        contentItem.isBookmark.value = isBookmarked
                    }
                )
            }
        }
    }

    private fun getScrapList() {
        viewDataBinding.layoutLoading.progressCl.visibility = View.VISIBLE
        lifecycleScope.launchWhenCreated {
            viewModel.getScrapList(
                onGetSuccess = {
                    viewDataBinding.layoutLoading.progressCl.visibility = View.GONE
                },
                onGetFail = {
                    toastShortOfFailMessage("스크랩 리스트 가져오기")
                    viewDataBinding.layoutLoading.progressCl.visibility = View.GONE
                }
            )
        }
    }
}