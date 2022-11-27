package com.vside.app.feature.filter

import android.os.Bundle
import android.view.View
import com.vside.app.R
import com.vside.app.databinding.DialogFilterSelectBinding
import com.vside.app.util.base.BaseBottomSheetDialogFragment
import org.koin.android.ext.android.inject

class FilterSelectFragment: BaseBottomSheetDialogFragment<DialogFilterSelectBinding, FilterSelectViewModel>() {
    override val layoutResId: Int = R.layout.dialog_filter_select
    override val viewModel: FilterSelectViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewModel = viewModel
    }
}