package com.vside.app.feature.common.view

import android.os.Bundle
import android.view.View
import com.vside.app.R
import com.vside.app.databinding.DialogOneBtnBinding
import com.vside.app.util.base.BaseDialogFragment
import org.koin.android.ext.android.inject

class OneBtnDialogFragment(
    private val contents: String,
    private val subContents: String = "",
    private val textBtn: String? = null
): BaseDialogFragment<DialogOneBtnBinding, OneBtnDialogViewModel>(280) {
    override val layoutResId: Int = R.layout.dialog_one_btn
    override val viewModel: OneBtnDialogViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewModel = viewModel

        initData()
        observeData()
    }

    private fun initData() {
        viewModel.contents.value = contents
        viewModel.subContents.value = subContents
        viewModel.textBtn.value = textBtn ?: requireContext().getString(R.string.confirm)
    }

    private fun observeData() {
        with(viewModel) {
            viewModel.isBtnClicked.observe(requireActivity()) {
                dismiss()
            }
        }
    }
}