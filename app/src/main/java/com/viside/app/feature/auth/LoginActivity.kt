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
import com.viside.app.util.auth.getKakaoLoginCallback
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

    private fun observeData() {
        val appCompatActivity = this@LoginActivity
        with(viewModel) {
            isKakaoClicked.observe(appCompatActivity) {
                fun afterKakaoLoginSuccess(kakaoOAuthToken: OAuthToken?) {
                    UserApiClient.instance.me { user, error ->
                        if(error != null) {
                            // 실패
                        }
                        else if (user != null) {
                            // 성공
                            lifecycleScope.launchWhenCreated {
                                val snsIdStr = user.id.toString()
                                val snsAccessToken = kakaoOAuthToken?.accessToken ?: ""

                                val nickname = user.kakaoAccount?.profile?.nickname
                                val email = user.kakaoAccount?.email
                                // 성별이랑 나이대는 enum 으로 만들어서 판별해야할 듯
                                val genderStr = VisideGender.getRequestStrByKakaoGender(user.kakaoAccount?.gender)
                                val ageRangeStr = VisideAgeRange.getRequestStrByKakaoAgeRangeObj(user.kakaoAccount?.ageRange)
                                val loginTypeStr = VisideLoginType.Kakao.serverDataStr

                                VisideLog.d("snsId $snsIdStr")
                                VisideLog.d("loginType $loginTypeStr")
                                VisideLog.d("nickname $nickname")
                                VisideLog.d("email $email")
                                VisideLog.d("gender $genderStr")
                                VisideLog.d("ageRange $ageRangeStr")
                            }
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
                }
                else {
                    UserApiClient.instance.loginWithKakaoAccount(
                        appCompatActivity,
                        callback = getKakaoLoginCallback(
                            onSuccess = {
                                afterKakaoLoginSuccess(it)
                            },
                            onFail = {

                            }
                        )
                    )
                }
            }
        }
    }
}