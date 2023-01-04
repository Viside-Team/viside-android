package com.vside.app.feature.auth

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.WindowCompat
import com.vside.app.R
import com.vside.app.databinding.ActivitySignUpBinding
import com.vside.app.util.base.BaseActivity
import com.vside.app.util.common.navigationHeight
import com.vside.app.util.common.statusBarHeight
import org.koin.android.ext.android.inject

class SignUpActivity : BaseActivity<ActivitySignUpBinding, SignUpViewModel>() {
    override val layoutResId: Int = R.layout.activity_sign_up
    override val viewModel: SignUpViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.viewModel = viewModel

        setUpWindow()
        viewDataBinding.etSignUpNickname.postDelayed({
            viewDataBinding.etSignUpNickname.isFocusableInTouchMode = true
            viewDataBinding.etSignUpNickname.requestFocus()
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(currentFocus, 0)
        },100)
    }

    override fun onKeyboardHeightChanged(height: Int, orientation: Int) {
        super.onKeyboardHeightChanged(height, orientation)

        viewDataBinding.clSignUpContent.setPaddingRelative(
            viewDataBinding.clSignUpContent.paddingStart,
            viewDataBinding.clSignUpContent.paddingTop,
            viewDataBinding.clSignUpContent.paddingEnd,
            height + navigationHeight(this),
        )

        val constraintSet = ConstraintSet().apply {
            clone(viewDataBinding.clSignUpContent)
            val verticalBias = if(viewModel.isKeyboardVisible.value == true) 0.8f else 0.5f
            setVerticalBias(viewDataBinding.tvSignUpTitle.id, verticalBias)
        }

        constraintSet.applyTo(viewDataBinding.clSignUpContent)
    }

    private fun setUpWindow() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT

        val layoutMargin = viewDataBinding.clSignUpContent.layoutParams as ViewGroup.MarginLayoutParams
        layoutMargin.setMargins(
            layoutMargin.leftMargin,
            layoutMargin.topMargin + statusBarHeight(this),
            layoutMargin.rightMargin,
            layoutMargin.bottomMargin
        )
    }

    fun nicknameDuplicationCheck() {

    }
}