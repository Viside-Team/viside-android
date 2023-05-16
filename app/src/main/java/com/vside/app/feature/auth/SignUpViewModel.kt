package com.vside.app.feature.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.ApiResponse
import com.vside.app.feature.auth.data.VsideUser
import com.vside.app.feature.auth.repo.AuthRepository
import com.vside.app.util.auth.PersonalInfoValidation
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.handleApiResponse
import com.vside.app.util.lifecycle.SingleLiveEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class SignUpViewModel(private val authRepository: AuthRepository) : BaseViewModel() {
    val nickname = MutableStateFlow("")

    val nicknameGuidance = MutableLiveData("")
    val isNicknameValidate = MutableLiveData<Boolean>()

    val passedVsideUser = MutableLiveData<VsideUser>()

    @ExperimentalCoroutinesApi
    @FlowPreview
    suspend fun nicknameValidationCheck() {
        nickname
            .debounce(300)
            .collect {
                nicknameGuidance.value = PersonalInfoValidation.nicknameGuidanceStr(it)
                isNicknameValidate.value = PersonalInfoValidation.nicknameValidationCheck(it)
                if (PersonalInfoValidation.nicknameValidationCheck(it)) {
                    viewModelScope.launch { nicknameDuplicationCheckAsync(it).join() }
                }
            }
    }


    suspend fun nicknameDuplicationCheckAsync(nickname: String) =
        viewModelScope.async {
            val response = authRepository.nicknameDuplicationCheck(nickname)
            when(response) {
                is ApiResponse.Success -> {
                    when (response.data?.isDuplicated) {
                        false -> {
                            nicknameGuidance.value =
                                PersonalInfoValidation.nicknameGuidanceStr(this@SignUpViewModel.nickname.value)
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
                }
                else -> {
                    isNicknameValidate.value = false
                }
            }
            response
        }



    suspend fun signUp(
        name: String?,
        email: String?,
        loginType: String?,
        gender: String?,
        ageRange: String?,
        snsId: String?,
        onPostSuccess: () -> Unit,
        onPostFail: () -> Unit
    ) {
        authRepository.signUp(name, email, loginType, gender, ageRange, snsId)
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