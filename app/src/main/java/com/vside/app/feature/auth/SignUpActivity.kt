package com.vside.app.feature.auth

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.vside.app.R
import com.vside.app.databinding.ActivitySignUpBinding
import com.vside.app.feature.MainActivity
import com.vside.app.feature.auth.data.request.SignInRequest
import com.vside.app.feature.auth.data.request.SignUpRequest
import com.vside.app.util.auth.PersonalInfoValidation
import com.vside.app.util.auth.storeUserInfo
import com.vside.app.util.base.BaseActivity
import com.vside.app.util.common.DataTransfer
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
        initData()
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

    private fun signUp(
        signUpRequest: SignUpRequest,
        onPostSuccess: () -> Unit,
        onPostFail: () -> Unit
    ) {
        lifecycleScope.launchWhenCreated {
            viewModel.signUp(
                signUpRequest,
                onPostSuccess,
                onPostFail
            )
        }
    }

    private fun signIn(
        signInRequest: SignInRequest,
        onOurUser: (jwtBearer: String?) -> Unit,
        onNewUser: () -> Unit,
        onPostFail: () -> Unit
    ) {
        lifecycleScope.launchWhenCreated {
            viewModel.signIn(
                signInRequest,
                onOurUser,
                onNewUser,
                onPostFail
            )
        }
    }

    private fun initUi() {
        setUpWindow()
        setUpNicknameEditText()
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun initData() {
        lifecycleScope.launchWhenCreated {
            viewModel.nicknameValidationCheck({}, {})
        }
        viewModel.passedVsideUser.value = intent.getParcelableExtra(DataTransfer.VSIDE_USER)
        lifecycleScope.launchWhenCreated {
            viewModel.initNickname(
                PersonalInfoValidation.convertToValidNickname(viewModel.passedVsideUser.value?.nickname),
                { viewDataBinding.etSignUpNickname.setSelection(viewDataBinding.etSignUpNickname.length()) },
                {})
        }
    }

    private fun observeData() {
        val appCompatActivity = this@SignUpActivity
        with(viewModel) {
            isStartClicked.observe(appCompatActivity) {
                val signUpRequest = SignUpRequest(
                    nickname.value,
                    passedVsideUser.value?.email,
                    passedVsideUser.value?.loginType,
                    passedVsideUser.value?.gender,
                    passedVsideUser.value?.ageRange,
                    passedVsideUser.value?.snsId
                )
                this@SignUpActivity.signUp(
                    signUpRequest,
                    onPostSuccess = {
                        val signInRequest = SignInRequest(
                            passedVsideUser.value?.loginType,
                            passedVsideUser.value?.snsId
                        )
                        this@SignUpActivity.signIn(
                            signInRequest,
                            onOurUser = { jwtBearer ->
                                jwtBearer?.let {
                                    storeUserInfo(
                                        appCompatActivity,
                                        jwtBearer,
                                        passedVsideUser.value?.snsId ?: ""
                                    )
                                }

                                val intent = Intent(
                                    this@SignUpActivity,
                                    MainActivity::class.java
                                )
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                startActivity(intent)
                            },
                            onNewUser = {},
                            onPostFail = {}
                        )
                    },
                    onPostFail = {
                        toastShortOfFailMessage("회원가입")
                    }
                )
            }
        }
    }

}