package com.vside.app.feature.content

import android.graphics.Color
import android.os.Bundle
import androidx.core.view.WindowCompat
import com.vside.app.R
import com.vside.app.databinding.ActivityContentBinding
import com.vside.app.util.base.BaseActivity
import org.koin.android.ext.android.inject

class ContentActivity : BaseActivity<ActivityContentBinding, ContentViewModel>() {
    override val layoutResId: Int = R.layout.activity_content
    override val viewModel: ContentViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpWindow()
        viewDataBinding.viewModel = viewModel
    }

    private fun setUpWindow() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
    }
}