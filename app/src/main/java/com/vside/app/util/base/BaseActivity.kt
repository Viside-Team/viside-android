package com.vside.app.util.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.vside.app.R
import com.vside.app.util.common.KeyboardHeightObserver
import com.vside.app.util.common.KeyboardHeightProvider
import com.vside.app.util.common.sharedpref.SharedPrefManager
import com.vside.app.util.log.VsideLog

abstract class BaseActivity<T: ViewDataBinding, VM: BaseViewModel> : AppCompatActivity(),
    KeyboardHeightObserver {
    lateinit var viewDataBinding: T

    abstract val layoutResId : Int
    abstract val viewModel: VM

    private val keyboardHeightProvider by lazy {
        KeyboardHeightProvider(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding = DataBindingUtil.setContentView(this, layoutResId)
        //LiveData의 업데이트를 위한 설정
        viewDataBinding.lifecycleOwner = this@BaseActivity
        viewModel.tokenBearer = SharedPrefManager.getString(this) { TOKEN_BEARER }

        viewDataBinding.root.post {
            keyboardHeightProvider.start()
        }

        observeData()
    }

    private fun observeData() {
        //토스트 메세지 띄우기
        viewModel.toastMessage.observe(this) {
            toastShort(it)
        }
    }

    override fun onKeyboardHeightChanged(height: Int, orientation: Int) {
        val isKeyboardVisible = height > 1
        viewModel.isKeyboardVisible.value = isKeyboardVisible
        if(!isKeyboardVisible) window.decorView.clearFocus()
    }

    override fun onPause() {
        super.onPause()
        keyboardHeightProvider.setKeyboardHeightObserver(null)
    }

    override fun onDestroy() {
        super.onDestroy()
        keyboardHeightProvider.close()
    }

    override fun onResume() {
        super.onResume()
        keyboardHeightProvider.setKeyboardHeightObserver(this)
    }

    fun toastShort(str:String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }

    fun toastShortOfFailMessage(themeKeyword: String) {
        toastShort(String.format(getString(R.string.failed_message),themeKeyword))
    }
}