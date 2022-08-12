package com.viside.app.util.auth

import com.kakao.sdk.auth.model.OAuthToken
import com.viside.app.util.log.VisideLog

// 카카오 로그인 콜백
fun getKakaoLoginCallback(
    onSuccess: (kakaoOAuthToken: OAuthToken?) -> Unit,
    onFail: () -> Unit
): (OAuthToken?, Throwable?) -> Unit = { token, error ->
    if (error != null) {
        VisideLog.e("로그인 실패",tr = error)
        onFail()
    } else if (token != null) {
        VisideLog.i("로그인 성공 ${token.accessToken}")
        onSuccess(token)
    }
}

