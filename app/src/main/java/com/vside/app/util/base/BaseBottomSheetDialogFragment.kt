package com.vside.app.util.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vside.app.R

abstract class BaseBottomSheetDialogFragment<T: ViewDataBinding, VM: BaseViewModel>: BottomSheetDialogFragment() {
    lateinit var viewDataBinding: T; private set

    abstract val layoutResId: Int
    abstract val viewModel: VM

    protected var toast : Toast? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        viewDataBinding.lifecycleOwner = viewLifecycleOwner

        return viewDataBinding.root
    }

    override fun getTheme(): Int {
        return R.style.VsideBottomSheetDialog
    }

    fun toastShort(str:String) {
        toast?.cancel()
        toast = Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT)
        toast?.show()
    }

    fun toastShortOfFailMessage(themeKeyword: String) {
        toastShort(String.format(getString(R.string.failed_message),themeKeyword))
    }

    fun showToastShort(toast: Toast) {
        this.toast?.cancel()
        this.toast = toast
        this.toast?.show()
    }
}