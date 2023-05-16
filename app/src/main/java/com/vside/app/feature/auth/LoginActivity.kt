package com.vside.app.feature.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.vside.app.R
import com.vside.app.databinding.ActivityLoginBinding
import com.vside.app.feature.MainActivity
import com.vside.app.feature.auth.data.VsideAgeRange
import com.vside.app.feature.auth.data.VsideGender
import com.vside.app.feature.auth.data.VsideLoginType
import com.vside.app.feature.auth.data.VsideUser
import com.vside.app.util.auth.getKakaoLoginCallback
import com.vside.app.util.auth.kakaoLogin
import com.vside.app.util.auth.removeUserInfo
import com.vside.app.util.auth.storeUserInfo
import com.vside.app.util.base.BaseActivity
import com.vside.app.util.common.DataTransfer
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
        loginType: String?,
        snsId: String?,
        onOurUser: (jwtBearer: String?) -> Unit,
        onNewUser: () -> Unit,
        onPostFail: () -> Unit
    ) {
        lifecycleScope.launchWhenCreated {
            viewModel.signIn(
                loginType,
                snsId,
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
                viewDataBinding.layoutLoading.progressCl.visibility = View.VISIBLE
                fun afterKakaoLoginSuccess(kakaoOAuthToken: OAuthToken?) {
                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            // 실패
                            viewDataBinding.layoutLoading.progressCl.visibility = View.GONE
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

                            this@LoginActivity.signIn(
                                loginTypeStr,
                                snsIdStr,
                                onOurUser = { jwtBearer ->
                                    jwtBearer?.let {
                                        storeUserInfo(
                                            appCompatActivity,
                                            jwtBearer,
                                            snsIdStr,
                                            loginTypeStr
                                        )
                                    }

                                    val intent = Intent(
                                        this@LoginActivity,
                                        MainActivity::class.java
                                    )
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

                                    viewDataBinding.layoutLoading.progressCl.visibility = View.GONE
                                    startActivity(intent)
                                },
                                onNewUser = {
                                    viewDataBinding.layoutLoading.progressCl.visibility = View.GONE
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
                                    viewDataBinding.layoutLoading.progressCl.visibility = View.GONE
                                    toastShortOfFailMessage("로그인")
                                }
                            )
                        }
                    }
                }
                kakaoLogin(
                    appCompatActivity,
                    getKakaoLoginCallback(
                        onSuccess = { afterKakaoLoginSuccess(it) },
                        onFail = {
                            viewDataBinding.layoutLoading.progressCl.visibility = View.GONE
                            toastShortOfFailMessage("카카오 로그인")
                        }
                    )
                )

            }

            isLookAroundClicked.observe(appCompatActivity) {
                removeUserInfo(appCompatActivity)
                val intent = Intent(
                    this@LoginActivity,
                    MainActivity::class.java
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        }
    }
}