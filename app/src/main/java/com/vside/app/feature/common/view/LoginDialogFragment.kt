package com.vside.app.feature.common.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.vside.app.R
import com.vside.app.databinding.DialogLoginBinding
import com.vside.app.feature.MainActivity
import com.vside.app.feature.auth.SignUpActivity
import com.vside.app.feature.auth.data.*
import com.vside.app.feature.auth.data.request.SignInRequest
import com.vside.app.util.auth.getKakaoLoginCallback
import com.vside.app.util.auth.kakaoLogin
import com.vside.app.util.auth.storeUserInfo
import com.vside.app.util.base.BaseBottomSheetDialogFragment
import com.vside.app.util.common.DataTransfer
import org.koin.android.ext.android.inject

class LoginDialogFragment:BaseBottomSheetDialogFragment<DialogLoginBinding, LoginDialogViewModel>() {
    override val layoutResId: Int = R.layout.dialog_login
    override val viewModel: LoginDialogViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewModel = viewModel
        dialog?.setCanceledOnTouchOutside(false)

        observeData()
    }

    fun signIn(signInRequest: SignInRequest,
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

    fun observeData() {
        with(viewModel) {
            isKakaoClicked.observe(viewLifecycleOwner) {
                fun afterKakaoLoginSuccess(kakaoOAuthToken: OAuthToken?) {
                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            // 실패
                            toastShortOfFailMessage("카카오 로그인")
                        } else if (user != null) {
                            // 성공

                            val snsIdStr = user.id.toString()
                            val snsAccessToken = kakaoOAuthToken?.accessToken ?: ""

                            val nickname = user.kakaoAccount?.profile?.nickname
                            val email = user.kakaoAccount?.email
                            // 성별이랑 나이대는 enum 으로 만들어서 판별해야할 듯
                            val genderStr =
                                VsideGender.getRequestStrByKakaoGender(user.kakaoAccount?.gender)
                            val ageRangeStr =
                                VsideAgeRange.getRequestStrByKakaoAgeRangeObj(user.kakaoAccount?.ageRange)
                            val loginTypeStr = VsideLoginType.Kakao.serverDataStr
                            val signInRequest = SignInRequest(
                                loginTypeStr,
                                snsIdStr
                            )

                            this@LoginDialogFragment.signIn(
                                signInRequest,
                                onOurUser = { jwtBearer ->
                                    jwtBearer?.let {
                                        storeUserInfo(
                                            requireContext(),
                                            jwtBearer,
                                            snsIdStr,
                                            loginTypeStr
                                        )
                                    }
                                    val intent = Intent(
                                        requireContext(),
                                        MainActivity::class.java
                                    )
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

                                    startActivity(intent)
                                },
                                onNewUser = {
                                    startActivity(
                                        Intent(
                                            requireContext(),
                                            SignUpActivity::class.java
                                        ).apply {
                                            putExtra(
                                                DataTransfer.VSIDE_USER,
                                                VsideUser(
                                                    nickname,
                                                    email,
                                                    loginTypeStr,
                                                    genderStr,
                                                    ageRangeStr,
                                                    snsIdStr
                                                )
                                            )
                                        })
                                },
                                onPostFail = {
                                    toastShortOfFailMessage("로그인")
                                }
                            )
                        }
                    }
                }
                kakaoLogin(
                    requireContext(),
                    getKakaoLoginCallback(
                        onSuccess = {
                            afterKakaoLoginSuccess(it)
                        },
                        onFail = {
                            toastShortOfFailMessage("카카오 로그인")
                        }
                    )
                )
            }
        }
    }
}