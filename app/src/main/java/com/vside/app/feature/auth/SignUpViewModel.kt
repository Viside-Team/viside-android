package com.vside.app.feature.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vside.app.feature.auth.data.VsideUser
import com.vside.app.feature.auth.data.request.NicknameDuplicationCheckRequest
import com.vside.app.feature.auth.data.request.SignInRequest
import com.vside.app.feature.auth.data.request.SignUpRequest
import com.vside.app.feature.auth.repo.AuthRepository
import com.vside.app.util.auth.PersonalInfoValidation
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.handleApiResponse
import com.vside.app.util.lifecycle.SingleLiveEvent
import com.vside.app.util.log.VsideLog
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce

class SignUpViewModel(private val authRepository: AuthRepository) : BaseViewModel() {
    val nickname = MutableStateFlow("")

    val nicknameGuidance = MutableLiveData("")
    val isNicknameValidate = MutableLiveData<Boolean>()

    val passedVsideUser = MutableLiveData<VsideUser>()

    @ExperimentalCoroutinesApi
    @FlowPreview
    suspend fun nicknameValidationCheck(onCheckSuccess: () -> Unit, onCheckFail: () -> Unit) {
        nickname
            .debounce(300)
            .collect {
                nicknameGuidance.value = PersonalInfoValidation.nicknameGuidanceStr(it)
                isNicknameValidate.value = PersonalInfoValidation.nicknameValidationCheck(it)
                if (PersonalInfoValidation.nicknameValidationCheck(it)) {
                    nicknameDuplicationCheck(it, onCheckSuccess, onCheckFail)
                }
            }
    }

    private suspend fun nicknameDuplicationCheck(
        nickname: String,
        onCheckSuccess: () -> Unit,
        onCheckFail: () -> Unit
    ) {
        authRepository.nicknameDuplicationCheck(NicknameDuplicationCheckRequest(nickname))
            .collect { response ->
                handleApiResponse(
                    response,
                    onSuccess = {
                        when (it.data?.isDuplicated) {
                            false -> {
                                nicknameGuidance.value =
                                    PersonalInfoValidation.nicknameGuidanceStr(this.nickname.value)
                                isNicknameValidate.value = true
                            }
                            true -> {
                                nicknameGuidance.value =
                                    PersonalInfoValidation.MSG_NICKNAME_DUPLICATED
                                isNicknameValidate.value = false
                            }
                            else -> {
                                // 서버에러
                                isNicknameValidate.value = false
                            }
                        }
                        onCheckSuccess()
                    },
                    onError = {
                        isNicknameValidate.value = false
                        onCheckFail()
                    },
                    onException = {
                        isNicknameValidate.value = false
                        onCheckFail()
                    }
                )
            }
    }


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

    private val _isStartClicked = SingleLiveEvent<Void>()
    val isStartClicked: LiveData<Void> = _isStartClicked

    fun onStartClick() {
        _isStartClicked.call()
    }
}