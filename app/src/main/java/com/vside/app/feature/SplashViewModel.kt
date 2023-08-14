package com.vside.app.feature

import com.vside.app.R
import com.vside.app.feature.auth.data.request.SignInRequest
import com.vside.app.feature.auth.repo.AuthRepository
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.handleApiResponse

class SplashViewModel(private val authRepository: AuthRepository): BaseViewModel() {
    val gifRawResId = R.raw.splash

    suspend fun signIn(
        signInRequest: SignInRequest,
        onOurUser: (jwtAccessBearer: String?, refreshToken: String?) -> Unit,
        onNewUser: () -> Unit,
        onPostFail: () -> Unit
    ) {
        authRepository.signIn(signInRequest)
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        if(it.data?.isOurUser == true) {
                            onOurUser(it.data?.jwtAccessBearer, it.data?.refreshToken)
                        }
                        else {
                            onNewUser()
                        }
                    },
                    onException = {
                        onPostFail()
                    },
                    onError = {
                        onPostFail()
                    }
                )
            }
    }
}