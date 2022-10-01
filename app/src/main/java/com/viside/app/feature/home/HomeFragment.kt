package com.viside.app.feature.home

import android.os.Bundle
import android.view.View
import com.viside.app.R
import com.viside.app.databinding.FragmentHomeBinding
import com.viside.app.util.base.BaseFragment
import org.koin.android.ext.android.inject

class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val layoutResId: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewModel = viewModel
    }
}