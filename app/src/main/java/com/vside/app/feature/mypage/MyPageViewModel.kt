package com.vside.app.feature.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.ApiResponse
import com.vside.app.feature.auth.data.request.WithdrawRequest
import com.vside.app.feature.common.data.ContentItem
import com.vside.app.feature.mypage.repo.MyPageRepository
import com.vside.app.util.base.BaseViewModel
import com.vside.app.util.common.ContentItemClickListener
import com.vside.app.util.lifecycle.SingleLiveEvent
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MyPageViewModel(private val myPageRepository: MyPageRepository): BaseViewModel(),
    ContentItemClickListener {
    private val _scrapContentList = MutableLiveData<List<ContentItem>>()
    val scrapContentList: LiveData<List<ContentItem>> = _scrapContentList

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    val isLoggedIn = MutableLiveData<Boolean>()

    private val myPageContentMaxCnt = 24
    fun getProfile() {
        viewModelScope.launch {
            val response = myPageRepository.getProfile(tokenBearer)
            when(response) {
                is ApiResponse.Success -> {
                    _userName.value = response.data?.userName
                }
                else -> {
                    _toastFailThemeKeyword.value = "프로필 정보 가져오기"
                }
            }
        }
    }

    fun getScrapList() {
        viewModelScope.launch {
            _isLoading.value = true
            val response = myPageRepository.getScrapList(tokenBearer)
            _isLoading.value = false
            when(response) {
                is ApiResponse.Success -> {
                    val contentListSize =
                        if(response.data?.contentList?.size != null) {
                            if(response.data?.contentList?.size!! > myPageContentMaxCnt) myPageContentMaxCnt
                            else response.data?.contentList?.size!!
                        }
                        else 0
                    val tempContentList = Array(contentListSize) { ContentItem () }

                    (0 until contentListSize).forEach { idx ->
                        if(idx < (contentListSize + 1) / 2) {
                            tempContentList[2 * idx] = ContentItem(response.data?.contentList!![idx])
                        } else {
                            tempContentList[2 * (idx - (contentListSize + 1) / 2) + 1] = ContentItem(response.data?.contentList!![idx])
                        }
                    }
                    _scrapContentList.value = tempContentList.toList()
                }
                else -> {
                    _toastFailThemeKeyword.value = "스크랩 리스트 가져오기"
                }
            }
        }
    }

    fun signOutAsync() : Deferred<ApiResponse<*>> =
        viewModelScope.async {
            _isLoading.value = true
            val response = myPageRepository.signOut(tokenBearer)
            _isLoading.value = false
            when(response) {
                is ApiResponse.Success -> {

                }
                else -> {
                    _toastFailThemeKeyword.value = "로그아웃"
                }
            }
            response
        }


    suspend fun withdrawAsync(withdrawRequest: WithdrawRequest) : Deferred<ApiResponse<*>> =
        viewModelScope.async {
            _isLoading.value = true
            val response = myPageRepository.withdraw(tokenBearer, withdrawRequest)
            _isLoading.value = false
            when(response) {
                is ApiResponse.Success -> {

                }
                else -> {
                    _toastFailThemeKeyword.value = "회원 탈퇴"
                }
            }
            response
        }


    // 클릭 이벤트들
    private val _isSeeAllClicked = SingleLiveEvent<Void>()
    val isSeeAllClicked: LiveData<Void> = _isSeeAllClicked

    fun onSeeAllClick() {
        _isSeeAllClicked.call()
    }

    private val _isLoginClicked = SingleLiveEvent<Void>()
    val isLoginClicked: LiveData<Void> = _isLoginClicked

    fun onLoginClick() {
        _isLoginClicked.call()
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

    private val _isContentItemClicked = SingleLiveEvent<ContentItem>()
    val isContentItemClicked: LiveData<ContentItem> = _isContentItemClicked

    override fun onContentItemClickListener(item: ContentItem) {
        _isContentItemClicked.value = item
    }
}