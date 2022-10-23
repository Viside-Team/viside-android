package com.vside.app.feature.common.view

import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.vside.app.R
import com.vside.app.databinding.DialogTwoBtnBinding
import com.vside.app.util.base.BaseDialogFragment
import org.koin.android.ext.android.inject

class TwoBtnDialogFragment(
    private val contents: String,
    private val subContents: String = "",
    private val leftTextBtn: String? = null,
    private val rightTextBtn: String? = null
) : BaseDialogFragment<DialogTwoBtnBinding, TwoBtnDialogViewModel>(280){
    override val layoutResId: Int = R.layout.dialog_two_btn
    override val viewModel: TwoBtnDialogViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewModel = viewModel

        initData()
    }

    private fun initData() {
        viewModel.contents.value = contents
        viewModel.subContents.value = subContents
        viewModel.leftTextBtn.value = leftTextBtn ?: requireContext().getString(R.string.confirm)
        viewModel.rightTextBtn.value = rightTextBtn ?: requireContext().getString(R.string.cancel)
    }

    fun setLeftTextColor(@ColorRes colorResId: Int) {
        lifecycleScope.launchWhenStarted {
            viewDataBinding.buttonTwoBtnDialogLeft.setTextColor(ContextCompat.getColor(requireContext(), colorResId))
        }
    }

    fun setRightTextColor(@ColorRes colorResId: Int) {
        lifecycleScope.launchWhenStarted {
            viewDataBinding.buttonTwoBtnDialogRight.setTextColor(ContextCompat.getColor(requireContext(), colorResId))
        }
    }
}