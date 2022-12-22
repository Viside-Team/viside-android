package com.vside.app.util.auth

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.vside.app.feature.MainActivity
import com.vside.app.util.common.sharedpref.SharedPrefManager
import com.vside.app.util.log.VsideLog

// 카카오 로그인 콜백
fun getKakaoLoginCallback(
    onSuccess: (kakaoOAuthToken: OAuthToken?) -> Unit,
    onFail: () -> Unit
): (OAuthToken?, Throwable?) -> Unit = { token, error ->
    if (error != null) {
        VsideLog.e("로그인 실패",tr = error)
        onFail()
    } else if (token != null) {
        VsideLog.i("로그인 성공 ${token.accessToken}")
        onSuccess(token)
    }
}

fun storeInfoAndStartHomeActivity(
    fragmentActivity: FragmentActivity,
    tokenBearer: String,
    snsId: String
) {
    SharedPrefManager.setString(fragmentActivity, { TOKEN_BEARER }, tokenBearer)
    SharedPrefManager.setString(fragmentActivity, { SNS_ID }, snsId)
    fragmentActivity.startActivity(Intent(fragmentActivity, MainActivity::class.java))
}

