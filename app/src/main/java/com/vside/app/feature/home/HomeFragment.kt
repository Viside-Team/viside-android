package com.vside.app.feature.home

import android.os.Bundle
import android.view.View
import com.vside.app.R
import com.vside.app.databinding.FragmentHomeBinding
import com.vside.app.util.base.BaseFragment
import org.koin.android.ext.android.inject

class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val layoutResId: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewModel = viewModel
    }
}