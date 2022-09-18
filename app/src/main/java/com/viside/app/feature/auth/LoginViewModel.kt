package com.viside.app.feature.auth

import androidx.lifecycle.LiveData
import com.viside.app.feature.auth.data.request.SignInRequest
import com.viside.app.feature.auth.data.request.SignUpRequest
import com.viside.app.feature.auth.repo.AuthRepository
import com.viside.app.util.base.BaseViewModel
import com.viside.app.util.common.handleApiResponse
import com.viside.app.util.lifecycle.SingleLiveEvent
import kotlinx.coroutines.flow.collect

class LoginViewModel(private val authRepository: AuthRepository) : BaseViewModel() {
    suspend fun signUp(
        signUpRequest: SignUpRequest,
        onPostSuccess: () -> Unit,
        onPostFail: () -> Unit
    ) {
        authRepository.signUp(signUpRequest)
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        if(it.data?.success == true) {
                            onPostSuccess()
                        }
                        else {
                            onPostFail()
                        }
                    }, onException = {
                        onPostFail()
                    }, onError = {
                        onPostFail()
                    }
                )
            }
    }

    suspend fun signIn(
        signInRequest: SignInRequest,
        onOurUser: (jwtBearer: String?) -> Unit,
        onNewUser: () -> Unit,
        onPostFail: () -> Unit
    ) {
        authRepository.signIn(signInRequest)
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        if(it.data?.isOurUser == true) {
                            onOurUser(it.data?.jwtBearer)
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

    /** 클릭 이벤트들 **/
    private val _isKakaoClicked = SingleLiveEvent<Void>()
    val isKakaoClicked: LiveData<Void> = _isKakaoClicked

    fun onKakaoClick() {
        _isKakaoClicked.call()
    }
}