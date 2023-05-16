package com.vside.app.feature.mypage

import android.content.Intent
import android.os.Bundle
import com.depayse.data.remote.mapper.toData
import com.vside.app.R
import com.vside.app.databinding.ActivityBookShelfBinding
import com.vside.app.feature.content.ContentActivity
import com.vside.app.util.base.BaseActivity
import com.vside.app.util.common.DataTransfer
import org.koin.android.ext.android.inject

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
        viewModel.getScrapList()
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
                        .putExtra(DataTransfer.CONTENT, it.toData())
                )
            }

            isContentBookmarkClicked.observe(appCompatActivity) {
                toggleScrapContent(it)
            }
        }
    }
}