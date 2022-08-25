package com.viside.app.feature.auth.data

import com.kakao.sdk.user.model.Gender

sealed class VisideGender(val serverDataStr: String) {
    object Male: VisideGender("MALE")
    object Female: VisideGender("FEMALE")

    companion object {
        fun getRequestStrByKakaoGender(kakaoGenderObj: Gender?): String? =
            when(kakaoGenderObj) {
                Gender.MALE -> Male.serverDataStr
                Gender.FEMALE -> Female.serverDataStr
                else -> null
            }
    }
}
