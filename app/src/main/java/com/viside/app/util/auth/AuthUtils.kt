package com.viside.app.util.auth

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.viside.app.feature.MainActivity
import com.viside.app.util.common.sharedpref.SharedPrefManager
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

fun storeInfoAndStartHomeActivity(
    fragmentActivity: FragmentActivity,
    tokenBearer: String
) {
    SharedPrefManager.setString(fragmentActivity, { TOKEN_BEARER }, tokenBearer)
    fragmentActivity.startActivity(Intent(fragmentActivity, MainActivity::class.java))
}

