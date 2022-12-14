package com.vside.app.util.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vside.app.util.common.sharedpref.SharedPrefManager

abstract class BaseBottomSheetDialogFragment<T: ViewDataBinding, VM: BaseViewModel>: BottomSheetDialogFragment() {
    lateinit var viewDataBinding: T; private set

    abstract val layoutResId: Int
    abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        viewDataBinding.lifecycleOwner = viewLifecycleOwner
        viewModel.tokenBearer = SharedPrefManager.getString(requireContext()) { TOKEN_BEARER }

        return viewDataBinding.root
    }
}