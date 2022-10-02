package com.vside.app.feature.auth.data

import com.kakao.sdk.user.model.Gender

sealed class VsideGender(val serverDataStr: String) {
    object Male: VsideGender("MALE")
    object Female: VsideGender("FEMALE")

    companion object {
        fun getRequestStrByKakaoGender(kakaoGenderObj: Gender?): String? =
            when(kakaoGenderObj) {
                Gender.MALE -> Male.serverDataStr
                Gender.FEMALE -> Female.serverDataStr
                else -> null
            }
    }
}
