package com.vside.app.feature.common.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.lifecycle.SingleLiveEvent

class TwoBtnDialogViewModel:BaseViewModel() {
    val contents = MutableLiveData<String>()
    val subContents = MutableLiveData<String>()
    val leftTextBtn = MutableLiveData<String>()
    val rightTextBtn = MutableLiveData<String>()

    private val _isLeftBtnClicked = SingleLiveEvent<Void>()
    val isLeftBtnClicked: LiveData<Void> = _isLeftBtnClicked

    private val _isRightBtnClicked = SingleLiveEvent<Void>()
    val isRightBtnClicked: LiveData<Void> = _isRightBtnClicked

    fun onLeftBtnClick() {
        _isLeftBtnClicked.call()
    }

    fun onRightBtnClick() {
        _isRightBtnClicked.call()
    }
}