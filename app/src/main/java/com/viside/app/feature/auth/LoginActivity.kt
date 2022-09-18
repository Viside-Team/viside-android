package com.viside.app.feature.auth

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.viside.app.R
import com.viside.app.databinding.ActivityLoginBinding
import com.viside.app.feature.auth.data.VisideAgeRange
import com.viside.app.feature.auth.data.VisideGender
import com.viside.app.feature.auth.data.VisideLoginType
import com.viside.app.feature.auth.data.request.SignInRequest
import com.viside.app.feature.auth.data.request.SignUpRequest
import com.viside.app.util.auth.getKakaoLoginCallback
import com.viside.app.util.auth.storeInfoAndStartHomeActivity
import com.viside.app.util.base.BaseActivity
import com.viside.app.util.log.VisideLog
import org.koin.android.ext.android.inject

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    override val layoutResId: Int
        get() = R.layout.activity_login
    override val viewModel: LoginViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.viewModel = viewModel

        observeData()
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

    private fun observeData() {
        val appCompatActivity = this@LoginActivity
        with(viewModel) {
            isKakaoClicked.observe(appCompatActivity) {
                fun afterKakaoLoginSuccess(kakaoOAuthToken: OAuthToken?) {
                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            // 실패
                        } else if (user != null) {
                            // 성공
                            val snsIdStr = user.id.toString()
                            val snsAccessToken = kakaoOAuthToken?.accessToken ?: ""

                            val nickname = user.kakaoAccount?.profile?.nickname
                            val email = user.kakaoAccount?.email
                            // 성별이랑 나이대는 enum 으로 만들어서 판별해야할 듯
                            val genderStr =
                                VisideGender.getRequestStrByKakaoGender(user.kakaoAccount?.gender)
                            val ageRangeStr =
                                VisideAgeRange.getRequestStrByKakaoAgeRangeObj(user.kakaoAccount?.ageRange)
                            val loginTypeStr = VisideLoginType.Kakao.serverDataStr
                            val signInRequest = SignInRequest(
                                loginTypeStr,
                                snsIdStr
                            )
                            this@LoginActivity.signIn(
                                signInRequest,
                                onOurUser = { jwtBearer ->
                                    jwtBearer?.let {
                                        storeInfoAndStartHomeActivity(appCompatActivity, jwtBearer)
                                    }
                                },
                                onNewUser = {
                                    val signUpRequest = SignUpRequest(
                                        nickname,
                                        email,
                                        loginTypeStr,
                                        genderStr,
                                        ageRangeStr,
                                        snsIdStr
                                    )
                                    this@LoginActivity.signUp(
                                        signUpRequest,
                                        onPostSuccess = {
                                            this@LoginActivity.signIn(
                                                signInRequest,
                                                onOurUser = { jwtBearer ->
                                                    jwtBearer?.let {
                                                        storeInfoAndStartHomeActivity(appCompatActivity, jwtBearer)
                                                    }
                                                },
                                                onNewUser = {},
                                                onPostFail = {}
                                            )
                                        },
                                        onPostFail = {
                                            toastShortOfFailMessage("회원가입")
                                        }
                                    )

                                },
                                onPostFail = {
                                    toastShortOfFailMessage("로그인")
                                }
                            )


                            VisideLog.d("snsId $snsIdStr")
                            VisideLog.d("loginType $loginTypeStr")
                            VisideLog.d("nickname $nickname")
                            VisideLog.d("email $email")
                            VisideLog.d("gender $genderStr")
                            VisideLog.d("ageRange $ageRangeStr")
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