package com.vside.app.feature.content

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.google.android.material.appbar.AppBarLayout
import com.vside.app.R
import com.vside.app.databinding.ActivityContentBinding
import com.vside.app.util.base.BaseActivity
import com.vside.app.util.common.statusBarHeight
import com.vside.app.util.common.webViewSetting
import org.koin.android.ext.android.inject
import kotlin.math.abs

class ContentActivity : BaseActivity<ActivityContentBinding, ContentViewModel>() {
    override val layoutResId: Int = R.layout.activity_content
    override val viewModel: ContentViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.viewModel = viewModel

        setUpWindow()
        initWebView()
        setUpCollapseListener()
        initData()
        observeData()
    }

    private fun initData() {
        viewModel.isLightBg.value = true
        viewModel.isBookmarked.value = true
    }

    private fun observeData() {
        val appCompatActivity = this@ContentActivity
        with(viewModel) {

        }
    }

    private fun setUpCollapseListener() {
        viewDataBinding.appBarLayoutContent.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                viewModel.isContentImgCollapsed.value = abs(verticalOffset) >= (appBarLayout?.totalScrollRange ?: 0)
            })
    }

    private fun setUpWindow() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT

        val layoutMargin = viewDataBinding.toolbarContent.layoutParams as ViewGroup.MarginLayoutParams
        layoutMargin.setMargins(
            layoutMargin.leftMargin,
            layoutMargin.topMargin + statusBarHeight(this),
            layoutMargin.rightMargin,
            layoutMargin.bottomMargin
        )
    }

    private fun initWebView() {
        webViewSetting(
            this@ContentActivity,
            viewDataBinding.webViewContent,
            viewDataBinding.progressCircularContent,
            "https://s3.ap-northeast-2.amazonaws.com/vside.contents/vegetarian/vside_vegetarian.html"
        )
    }
}