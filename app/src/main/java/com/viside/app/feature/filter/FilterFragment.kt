package com.viside.app.feature.filter

import android.os.Bundle
import android.view.View
import com.viside.app.R
import com.viside.app.databinding.FragmentFilterBinding
import com.viside.app.util.base.BaseFragment
import org.koin.android.ext.android.inject

class FilterFragment: BaseFragment<FragmentFilterBinding, FilterViewModel>() {
    override val layoutResId: Int = R.layout.fragment_filter
    override val viewModel: FilterViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewModel = viewModel
    }
}