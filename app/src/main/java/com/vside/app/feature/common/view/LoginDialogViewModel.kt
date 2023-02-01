package com.vside.app.feature.common.view

import androidx.lifecycle.LiveData
import com.vside.app.feature.auth.data.request.SignInRequest
import com.vside.app.feature.auth.repo.AuthRepository
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.handleApiResponse
import com.vside.app.util.lifecycle.SingleLiveEvent
import kotlinx.coroutines.flow.collect

class LoginDialogViewModel(private val authRepository: AuthRepository) : BaseViewModel() {
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
                        if (it.data?.isOurUser == true) {
                            onOurUser(it.data?.jwtBearer)
                        } else {
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