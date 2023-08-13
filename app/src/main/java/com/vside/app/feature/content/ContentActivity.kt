package com.vside.app.feature.content

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import android.view.ViewGroup
import android.view.WindowInsetsController
import androidx.core.view.WindowCompat
import com.google.android.material.appbar.AppBarLayout
import com.vside.app.R
import com.vside.app.databinding.ActivityContentBinding
import com.vside.app.feature.common.data.Content
import com.vside.app.feature.common.view.LoginDialogFragment
import com.vside.app.util.base.BaseActivity
import com.vside.app.util.common.DataTransfer
import com.vside.app.util.common.sharedpref.SharedPrefManager
import com.vside.app.util.common.statusBarHeight
import com.vside.app.util.common.webViewSetting
import org.koin.android.ext.android.inject
import java.math.BigInteger
import kotlin.math.abs

class ContentActivity : BaseActivity<ActivityContentBinding, ContentViewModel>() {
    override val layoutResId: Int = R.layout.activity_content
    override val viewModel: ContentViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.viewModel = viewModel

        initData()
        setUpWindow()
        initWebView()
        setUpCollapseListener()
        observeData()
    }

    private fun initData() {
        intent.getParcelableExtra<Content>(DataTransfer.CONTENT)?.let {
            viewModel.contentUrl = it.contentUrl ?: ""
            viewModel.title.value = it.title ?: ""
            viewModel.dateStr.value = it.dateStr?.replace("-", ". ") ?: ""
            viewModel.isBookmarked.value = it.isBookmark
            viewModel.contentImgUrl.value = it.contentImgUrl
            viewModel.isLightBg.value = it.isLightBg
            viewModel.contentId = it.contentId ?: BigInteger.ZERO
        }
    }

    private fun observeData() {
        val appCompatActivity = this@ContentActivity
        with(viewModel) {
            isContentImgCollapsed.observe(appCompatActivity) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if(it==true) {
                        window.insetsController?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
                    }
                    else {
                        val systemUiAppearance =
                            if(viewModel.isLightBg.value == true) WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                            else 0
                        window.insetsController?.setSystemBarsAppearance(systemUiAppearance, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
                    }
                }
                else {
                    if(it==true) {
                        window.decorView.systemUiVisibility = window.decorView.systemUiVisibility and SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                    }
                    else {
                        val systemUiVisibilityFlags =
                            if(viewModel.isLightBg.value == true) window.decorView.systemUiVisibility and SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                            else window.decorView.systemUiVisibility or SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                        window.decorView.systemUiVisibility = systemUiVisibilityFlags
                    }
                }
            }
            isBookmarkClicked.observe(appCompatActivity) {
                if(SharedPrefManager.getBoolean(appCompatActivity) { IS_LOGGED_IN }) {
                    viewModel.toggleContentScrap()
                    return@observe
                }

                val loginDialog = LoginDialogFragment()
                loginDialog.show(supportFragmentManager, loginDialog.tag)
            }

            isBackClicked.observe(appCompatActivity) {
                onBackPressed()
            }
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
        viewDataBinding.layoutLoading.progressCl.visibility = View.VISIBLE
        viewDataBinding.layoutLoading.progressCl.setBackgroundColor(Color.WHITE)
        webViewSetting(
            this@ContentActivity,
            viewDataBinding.webViewContent,
            viewDataBinding.layoutLoading.progressCl,
            viewModel.contentUrl
        )
    }
}