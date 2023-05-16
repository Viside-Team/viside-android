package com.vside.app.feature.common.view

import androidx.lifecycle.LiveData
import com.vside.app.feature.auth.repo.AuthRepository
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.handleApiResponse
import com.vside.app.util.lifecycle.SingleLiveEvent

class LoginDialogViewModel(private val authRepository: AuthRepository) : BaseViewModel() {
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
}