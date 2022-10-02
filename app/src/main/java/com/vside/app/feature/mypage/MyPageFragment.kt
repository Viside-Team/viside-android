package com.vside.app.feature.mypage

import android.os.Bundle
import android.view.View
import com.vside.app.R
import com.vside.app.databinding.FragmentMyPageBinding
import com.vside.app.util.base.BaseFragment
import org.koin.android.ext.android.inject

class MyPageFragment: BaseFragment<FragmentMyPageBinding, MyPageViewModel>() {
    override val layoutResId: Int = R.layout.fragment_my_page
    override val viewModel: MyPageViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewModel = viewModel
    }
}