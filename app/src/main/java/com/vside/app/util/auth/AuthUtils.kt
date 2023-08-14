package com.vside.app.util.auth

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.vside.app.util.common.sharedpref.SharedPrefManager
import com.vside.app.util.log.VsideLog

// 카카오 로그인 콜백
fun getKakaoLoginCallback(
    onSuccess: (kakaoOAuthToken: OAuthToken?) -> Unit,
    onFail: () -> Unit
): (OAuthToken?, Throwable?) -> Unit = { token, error ->
    if (error != null) {
        VsideLog.e("로그인 실패", tr = error)
        onFail()
    } else if (token != null) {
        VsideLog.i("로그인 성공 ${token.accessToken}")
        onSuccess(token)
    }
}

fun kakaoLogin(context: Context, callback: (token: OAuthToken?, error: Throwable?) -> Unit) {
    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
        UserApiClient.instance.loginWithKakaoTalk(
            context,
            callback = callback
        )
    } else {
        UserApiClient.instance.loginWithKakaoAccount(
            context,
            callback = callback
        )
    }
}

fun updateJwtAccessBearer(
    context: Context, jwtAccessBearer: String
) {
    SharedPrefManager.setString(context, { JWT_ACCESS_BEARER }, jwtAccessBearer)
}

fun storeUserInfo(
    context: Context,
    jwtAccessBearer: String,
    refreshToken: String,
    snsId: String,
    loginTypeStr: String
) {
    SharedPrefManager.setBoolean(context, { IS_LOGGED_IN }, true)
    SharedPrefManager.setString(context, { LOGIN_TYPE }, loginTypeStr)
    SharedPrefManager.setString(context, { JWT_ACCESS_BEARER }, jwtAccessBearer)
    SharedPrefManager.setString(context, { REFRESH_TOKEN }, refreshToken)
    SharedPrefManager.setString(context, { SNS_ID }, snsId)
}

fun removeUserInfo(context: Context) {
    SharedPrefManager.removeBoolean(context) { IS_LOGGED_IN }
    SharedPrefManager.removeString(context) { LOGIN_TYPE }
    SharedPrefManager.removeString(context) { JWT_ACCESS_BEARER }
    SharedPrefManager.removeString(context) { REFRESH_TOKEN }
    SharedPrefManager.removeString(context) { SNS_ID }
}