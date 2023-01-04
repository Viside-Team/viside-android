package com.vside.app.feature.auth

import com.vside.app.feature.auth.repo.AuthRepository
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.handleApiResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce

class SignUpViewModel(private val authRepository: AuthRepository) : BaseViewModel() {
    val nickname = MutableStateFlow("")

    @ExperimentalCoroutinesApi
    @FlowPreview
    suspend fun nicknameValidationCheck() {
        nickname
            .debounce(300)
    }

    suspend fun nicknameDuplicationCheck(onCheckSuccess: () -> Unit, onCheckFail: () -> Unit) {
        authRepository.nicknameDuplicationCheck()
            .collect { response ->
                handleApiResponse(
                    response,
                    onSuccess = {
                        onCheckSuccess()
                    },
                    onError = {
                        onCheckFail()
                    },
                    onException = {
                        onCheckFail()
                    }
                )
            }
    }
}