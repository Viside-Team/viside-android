package com.vside.app.feature

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.vside.app.R
import com.vside.app.databinding.ActivitySplashBinding
import com.vside.app.feature.auth.LoginActivity
import com.vside.app.feature.auth.data.request.SignInRequest
import com.vside.app.util.auth.removeUserInfo
import com.vside.app.util.auth.storeUserInfo
import com.vside.app.util.base.BaseActivity
import com.vside.app.util.common.sharedpref.SharedPrefManager
import org.koin.android.ext.android.inject

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {
    override val layoutResId: Int = R.layout.activity_splash
    override val viewModel: SplashViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.viewModel = viewModel

        initUi()
        checkLoginAndStartActivity()
    }

    private fun checkLoginAndStartActivity() {
        val appCompatActivity = this@SplashActivity
        if (SharedPrefManager.getBoolean(this) { IS_LOGGED_IN }) {
            val loginType = SharedPrefManager.getString(appCompatActivity) { LOGIN_TYPE }
            val snsIdStr = SharedPrefManager.getString(appCompatActivity) { SNS_ID }
            signIn(
                SignInRequest(
                    loginType,
                    snsIdStr
                ),
                onOurUser = { jwtBearer ->
                    jwtBearer?.let {
                        storeUserInfo(
                            appCompatActivity,
                            jwtBearer,
                            snsIdStr,
                            loginType
                        )
                    }

                    val intent = Intent(
                        appCompatActivity,
                        MainActivity::class.java
                    )
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                },
                onNewUser = {
                    removeUserInfo(appCompatActivity)
                    startActivity(
                        Intent(
                            appCompatActivity,
                            LoginActivity::class.java
                        ).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        }
                    )
                },
                onPostFail = {
                    removeUserInfo(appCompatActivity)
                    startActivity(
                        Intent(
                            appCompatActivity,
                            LoginActivity::class.java
                        ).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        }
                    )
                }
            )
        }
        else {
            startActivity(
                Intent(
                    appCompatActivity,
                    LoginActivity::class.java
                ).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                }
            )
        }
    }

    private fun initUi() {
        setupWindow()
    }

    private fun setupWindow() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
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
}