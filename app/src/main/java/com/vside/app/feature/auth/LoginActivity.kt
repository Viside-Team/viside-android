package com.vside.app.feature.auth

import android.content.Intent
import android.os.Bundle
import android.view.WindowInsetsController
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.vside.app.R
import com.vside.app.databinding.ActivityLoginBinding
import com.vside.app.feature.auth.data.VsideAgeRange
import com.vside.app.feature.auth.data.VsideGender
import com.vside.app.feature.auth.data.VsideLoginType
import com.vside.app.feature.auth.data.VsideUser
import com.vside.app.feature.auth.data.request.SignInRequest
import com.vside.app.feature.auth.data.request.SignUpRequest
import com.vside.app.util.auth.getKakaoLoginCallback
import com.vside.app.util.auth.storeInfoAndStartHomeActivity
import com.vside.app.util.base.BaseActivity
import com.vside.app.util.common.DataTransfer
import com.vside.app.util.log.VsideLog
import org.koin.android.ext.android.inject

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    override val layoutResId: Int = R.layout.activity_login
    override val viewModel: LoginViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.viewModel = viewModel

        setUpStatusBarStyle()

        observeData()
    }

    private fun setUpStatusBarStyle() {
        // 상태바 색 검정
        window.statusBarColor = getColor(R.color.black)
        // 텍스트 색 하얗게
        val windowInsetController = ViewCompat.getWindowInsetsController(window.decorView)
        windowInsetController?.isAppearanceLightStatusBars = false
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

    private fun observeData() {
        val appCompatActivity = this@LoginActivity
        with(viewModel) {
            isKakaoClicked.observe(appCompatActivity) {
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

                            this@LoginActivity.signIn(
                                signInRequest,
                                onOurUser = { jwtBearer ->
                                    jwtBearer?.let {
                                        storeInfoAndStartHomeActivity(
                                            appCompatActivity,
                                            jwtBearer,
                                            snsIdStr
                                        )
                                    }
                                },
                                onNewUser = {
                                    startActivity(
                                        Intent(
                                            appCompatActivity,
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

                            VsideLog.d("snsId $snsIdStr")
                            VsideLog.d("loginType $loginTypeStr")
                            VsideLog.d("nickname $nickname")
                            VsideLog.d("email $email")
                            VsideLog.d("gender $genderStr")
                            VsideLog.d("ageRange $ageRangeStr")
                        }
                    }
                }
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(appCompatActivity)) {
                    UserApiClient.instance.loginWithKakaoTalk(
                        appCompatActivity,
                        callback = getKakaoLoginCallback(
                            onSuccess = {
                                afterKakaoLoginSuccess(it)
                            },
                            onFail = {
                                toastShortOfFailMessage("카카오 로그인")
                            }
                        ),
                    )
                } else {
                    UserApiClient.instance.loginWithKakaoAccount(
                        appCompatActivity,
                        callback = getKakaoLoginCallback(
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
}