package com.vside.app.feature.common.view

import android.os.Bundle
import android.view.View
import com.vside.app.R
import com.vside.app.databinding.DialogLoginBinding
import com.vside.app.util.base.BaseBottomSheetDialogFragment
import org.koin.android.ext.android.inject

class LoginDialogFragment:BaseBottomSheetDialogFragment<DialogLoginBinding, LoginDialogViewModel>() {
    override val layoutResId: Int = R.layout.dialog_login
    override val viewModel: LoginDialogViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewModel = viewModel
    }
}