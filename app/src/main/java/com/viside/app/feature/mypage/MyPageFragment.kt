package com.viside.app.feature.mypage

import android.os.Bundle
import android.view.View
import com.viside.app.R
import com.viside.app.databinding.FragmentMyPageBinding
import com.viside.app.util.base.BaseFragment
import org.koin.android.ext.android.inject

class MyPageFragment: BaseFragment<FragmentMyPageBinding, MyPageViewModel>() {
    override val layoutResId: Int = R.layout.fragment_my_page
    override val viewModel: MyPageViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewModel = viewModel
    }
}