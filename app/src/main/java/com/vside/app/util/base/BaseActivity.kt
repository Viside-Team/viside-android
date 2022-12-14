package com.vside.app.util.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.vside.app.R
import com.vside.app.util.common.sharedpref.SharedPrefManager

abstract class BaseActivity<T: ViewDataBinding, VM: BaseViewModel> : AppCompatActivity() {
    lateinit var viewDataBinding: T

    abstract val layoutResId : Int
    abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding = DataBindingUtil.setContentView(this, layoutResId)
        //LiveData의 업데이트를 위한 설정
        viewDataBinding.lifecycleOwner = this@BaseActivity
        viewModel.tokenBearer = SharedPrefManager.getString(this) { TOKEN_BEARER }

        observeData()
    }

    private fun observeData() {
        //토스트 메세지 띄우기
        viewModel.toastMessage.observe(this) {
            toastShort(it)
        }
    }

    fun toastShort(str:String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }

    fun toastShortOfFailMessage(themeKeyword: String) {
        toastShort(String.format(getString(R.string.failed_message),themeKeyword))
    }
}