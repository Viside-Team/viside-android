package com.vside.app.util.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.vside.app.R
import com.vside.app.util.common.sharedpref.SharedPrefManager

abstract class BaseFragment<T: ViewDataBinding, VM: BaseViewModel>: Fragment() {
    lateinit var viewDataBinding: T

    abstract val layoutResId : Int
    abstract val viewModel : VM

    protected var toast : Toast? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        // 라이브 데이터 업데이트를 위한 설정
        viewDataBinding.lifecycleOwner = viewLifecycleOwner
        viewModel.tokenBearer = SharedPrefManager.getString(requireContext()) { TOKEN_BEARER}

        observeData()

        return viewDataBinding.root
    }

    private fun observeData() {
        //토스트 메세지 띄우기
        viewModel.toastMessage.observe(viewLifecycleOwner) {
            toastShort(it)
        }
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