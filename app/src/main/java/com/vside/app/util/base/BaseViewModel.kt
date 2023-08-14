package com.vside.app.util.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    val isKeyboardVisible = MutableLiveData<Boolean>()

    protected val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    protected val _toastFailThemeKeyword = MutableLiveData<String>()
    val toastFailThemeKeyword: LiveData<String> = _toastFailThemeKeyword

    protected val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
}