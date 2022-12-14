package com.vside.app.feature.mypage

import androidx.lifecycle.LiveData
import com.vside.app.feature.auth.data.request.WithdrawRequest
import com.vside.app.feature.mypage.repo.MyPageRepository
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.handleApiResponse
import com.vside.app.util.lifecycle.SingleLiveEvent
import kotlinx.coroutines.flow.collect

class MyPageViewModel(private val myPageRepository: MyPageRepository): BaseViewModel() {
    suspend fun getScrapList(onGetSuccess: () -> Unit, onGetFail: () -> Unit) {
        myPageRepository.getScrapList(tokenBearer)
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        onGetSuccess()
                    },
                    onError = {
                        onGetFail()
                    }, onException = {
                        onGetFail()
                    }
                )
            }
    }

    suspend fun signOut(onSignOutSuccess: () -> Unit, onSignOutFail: () -> Unit) {
        myPageRepository.signOut(tokenBearer)
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        onSignOutSuccess()
                    },
                    onException = {
                        onSignOutFail()
                    },
                    onError = {
                        onSignOutFail()
                    }
                )
            }
    }

    suspend fun withdraw(withdrawRequest: WithdrawRequest, onWithdrawSuccess: () -> Unit, onWithdrawFail: () -> Unit) {
        myPageRepository.withdraw(tokenBearer, withdrawRequest)
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        onWithdrawSuccess()
                    },
                    onError = {
                        onWithdrawFail()
                    },
                    onException = {
                        onWithdrawFail()
                    }
                )
            }
    }

    // 클릭 이벤트들
    private val _isSeeAllClicked = SingleLiveEvent<Void>()
    val isSeeAllClicked: LiveData<Void> = _isSeeAllClicked

    fun onSeeAllClick() {
        _isSeeAllClicked.call()
    }

    private val _isInquiryClicked = SingleLiveEvent<Void>()
    val isInquiryClicked: LiveData<Void> = _isInquiryClicked

    fun onInquiryClick() {
        _isInquiryClicked.call()
    }

    private val _isTermsOfServiceClicked = SingleLiveEvent<Void>()
    val isTermsOfServiceClicked: LiveData<Void> = _isTermsOfServiceClicked

    fun onTermsOfServiceClick() {
        _isTermsOfServiceClicked.call()
    }

    private val _isPrivacyPolicyClicked = SingleLiveEvent<Void>()
    val isPrivacyPolicyClicked: LiveData<Void> = _isPrivacyPolicyClicked

    fun onPrivacyPolicyClick() {
        _isPrivacyPolicyClicked.call()
    }

    private val _isLogoutClicked = SingleLiveEvent<Void>()
    val isLogoutClicked: LiveData<Void> = _isLogoutClicked

    fun onLogoutClick() {
        _isLogoutClicked.call()
    }

    private val _isWithDrawClicked = SingleLiveEvent<Void>()
    val isWithDrawClicked: LiveData<Void> = _isWithDrawClicked

    fun onWithDrawClick() {
        _isWithDrawClicked.call()
    }

}