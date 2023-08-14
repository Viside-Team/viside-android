package com.vside.app.util.base

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.vside.app.util.dpToPx

abstract class BaseDialogFragment<T: ViewDataBinding, VM: BaseViewModel>(private val widthDp: Int? = null) : DialogFragment() {
    lateinit var viewDataBinding: T; private set

    abstract val layoutResId: Int
    abstract val viewModel: VM

    fun interface OnBaseDialogFragmentDismissListener {
        fun onDismiss()
    }

    private lateinit var onBaseDialogFragmentDismissListener: OnBaseDialogFragmentDismissListener
    fun setOnBaseDialogFragmentDismissListener(onBaseDialogFragmentDismissListener: OnBaseDialogFragmentDismissListener) {
        this.onBaseDialogFragmentDismissListener = onBaseDialogFragmentDismissListener
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if(this::onBaseDialogFragmentDismissListener.isInitialized) {
            onBaseDialogFragmentDismissListener.onDismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        viewDataBinding.lifecycleOwner = viewLifecycleOwner

        return viewDataBinding.root
    }

    override fun onResume() {
        super.onResume()
        setUpDialogUi()
    }

    private fun setUpDialogUi() {
        widthDp?.let {
            dialog?.window?.setLayout(
                dpToPx(requireContext(), it.toFloat()).toInt(),
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}