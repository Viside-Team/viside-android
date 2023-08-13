package com.vside.app.feature.auth

import androidx.lifecycle.LiveData
import com.depayse.data.repository.AuthRepositoryImpl
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.handleApiResponse
import com.vside.app.util.lifecycle.SingleLiveEvent

class LoginViewModel(private val authRepository: AuthRepositoryImpl) : BaseViewModel() {

    suspend fun signIn(
        loginType: String?,
        snsId: String?,
        onOurUser: (jwtBearer: String?) -> Unit,
        onNewUser: () -> Unit,
        onPostFail: () -> Unit
    ) {
        authRepository.signIn(loginType, snsId)
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

    private val _isLookAroundClicked = SingleLiveEvent<Void>()
    val isLookAroundClicked: LiveData<Void> = _isLookAroundClicked

    fun onLookAroundClick() {
        _isLookAroundClicked.call()
    }
}