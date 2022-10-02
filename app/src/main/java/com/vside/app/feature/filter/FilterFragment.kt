package com.vside.app.feature.filter

import android.os.Bundle
import android.view.View
import com.vside.app.R
import com.vside.app.databinding.FragmentFilterBinding
import com.vside.app.util.base.BaseFragment
import org.koin.android.ext.android.inject

class FilterFragment: BaseFragment<FragmentFilterBinding, FilterViewModel>() {
    override val layoutResId: Int = R.layout.fragment_filter
    override val viewModel: FilterViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewModel = viewModel
    }
}