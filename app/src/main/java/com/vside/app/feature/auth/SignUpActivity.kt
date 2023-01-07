package com.vside.app.feature.auth

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.WindowCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.vside.app.R
import com.vside.app.databinding.ActivitySignUpBinding
import com.vside.app.util.auth.PersonalInfoValidation
import com.vside.app.util.base.BaseActivity
import com.vside.app.util.common.navigationHeight
import com.vside.app.util.common.statusBarHeight
import com.vside.app.util.log.VsideLog
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.android.inject

class SignUpActivity : BaseActivity<ActivitySignUpBinding, SignUpViewModel>() {
    override val layoutResId: Int = R.layout.activity_sign_up
    override val viewModel: SignUpViewModel by inject()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.viewModel = viewModel

        initUi()
        observeData()

        lifecycleScope.launchWhenCreated {
            viewModel.nicknameValidationCheck({}, {})
        }
    }

    private fun setUpNicknameEditText() {
        viewDataBinding.etSignUpNickname.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (viewModel.isNicknameValidate.value == true) {
                    VsideLog.d("입력 감지")
                }
                return@setOnEditorActionListener true
            }
            false
        }
    }

    override fun onResume() {
        super.onResume()
        showKeyboard()
    }

    private fun showKeyboard() {
        viewDataBinding.etSignUpNickname.postDelayed({
            viewDataBinding.etSignUpNickname.isFocusableInTouchMode = true
            viewDataBinding.etSignUpNickname.requestFocus()
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
                currentFocus,
                0
            )
        }, 150)
    }

    override fun onKeyboardHeightChanged(height: Int, orientation: Int) {
        super.onKeyboardHeightChanged(height, orientation)

        val realHeight = if (viewModel.isKeyboardVisible.value == true) height else 0

        viewDataBinding.clSignUpContent.setPaddingRelative(
            viewDataBinding.clSignUpContent.paddingStart,
            viewDataBinding.clSignUpContent.paddingTop,
            viewDataBinding.clSignUpContent.paddingEnd,
            realHeight + navigationHeight(this),
        )

        val constraintSet = ConstraintSet().apply {
            clone(viewDataBinding.clSignUpContent)
            val verticalBias = if (viewModel.isKeyboardVisible.value == true) 0.8f else 0.5f
            setVerticalBias(viewDataBinding.tvSignUpTitle.id, verticalBias)
        }

        constraintSet.applyTo(viewDataBinding.clSignUpContent)
    }

    private fun setUpWindow() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT

        val layoutMargin =
            viewDataBinding.clSignUpContent.layoutParams as ViewGroup.MarginLayoutParams
        layoutMargin.setMargins(
            layoutMargin.leftMargin,
            layoutMargin.topMargin + statusBarHeight(this),
            layoutMargin.rightMargin,
            layoutMargin.bottomMargin
        )
    }

    private fun initUi() {
        setUpWindow()
        setUpNicknameEditText()
    }

    private fun observeData() {
        val appCompatActivity = this@SignUpActivity
        with(viewModel) {
            isStartClicked.observe(appCompatActivity) {

            }
        }
    }

}