package com.vside.app.feature.common.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.lifecycle.SingleLiveEvent

class OneBtnDialogViewModel: BaseViewModel() {
    val contents = MutableLiveData<String>()
    val subContents = MutableLiveData<String>()
    val textBtn = MutableLiveData<String>()

    private val _isBtnClicked = SingleLiveEvent<Void>()
    val isBtnClicked: LiveData<Void> = _isBtnClicked

    fun onBtnClick() {
        _isBtnClicked.call()
    }
}