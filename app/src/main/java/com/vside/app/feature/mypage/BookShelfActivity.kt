package com.vside.app.feature.mypage

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.vside.app.R
import com.vside.app.databinding.ActivityBookShelfBinding
import com.vside.app.util.base.BaseActivity
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
        getScrapList()
    }

    private fun observeData() {
        val appCompatActivity = this@BookShelfActivity
        with(viewModel) {
            isBackClicked.observe(appCompatActivity) {
                onBackPressed()
            }
        }
    }

    private fun getScrapList() {
        lifecycleScope.launchWhenCreated {
            viewModel.getScrapList(
                onGetSuccess = {},
                onGetFail = { toastShortOfFailMessage("스크랩 리스트 가져오기") }
            )
        }
    }
}