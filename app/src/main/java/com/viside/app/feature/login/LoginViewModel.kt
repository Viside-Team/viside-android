package com.viside.app.feature.login

import androidx.lifecycle.LiveData
import com.viside.app.util.base.BaseViewModel
import com.viside.app.util.lifecycle.SingleLiveEvent

class LoginViewModel:BaseViewModel() {
    private val _isKakaoClicked = SingleLiveEvent<Void>()
    val isKakaoClicked: LiveData<Void> =  _isKakaoClicked

    fun onKakaoClick() {
        _isKakaoClicked.call()
    }
}