package com.viside.app.feature

import android.os.Bundle
import com.viside.app.R
import com.viside.app.databinding.ActivityMainBinding
import com.viside.app.util.base.BaseActivity
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val layoutResId: Int = R.layout.activity_main
    override val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.viewModel = viewModel

        viewDataBinding.bnvMain.itemIconTintList = null
    }
}