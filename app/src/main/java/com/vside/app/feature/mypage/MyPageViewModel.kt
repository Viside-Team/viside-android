package com.vside.app.feature.mypage

import androidx.lifecycle.LiveData
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.lifecycle.SingleLiveEvent

class MyPageViewModel: BaseViewModel(){

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