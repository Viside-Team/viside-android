package com.vside.app.util.auth

import android.content.Context
import com.google.gson.Gson
import com.vside.app.feature.auth.data.response.SignInResponse
import com.vside.app.util.common.sharedpref.SharedPrefManager
import com.vside.app.util.log.VsideLog
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(private val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val authRequest = buildAuthRequest(context, originalRequest)

        var response = chain.proceed(authRequest)
        if(response.isSuccessful) {
            try {
                val bodyStr = response.peekBody(Long.MAX_VALUE).string()
                val signInResponse = Gson().fromJson(bodyStr, SignInResponse::class.java)
                if(signInResponse.jwtAccessBearer != null && signInResponse.refreshToken == null) {
                    updateJwtAccessBearer(context, signInResponse.jwtAccessBearer)
                    // AccessToken 업데이트 이후 재요청
                    response = chain.proceed(buildAuthRequest(context, originalRequest))
                }
            }
            catch (e:Exception) {
                VsideLog.e("$e")
            }
        }
        return response
    }

    private fun isAuthNeeded(originalRequest: Request): Boolean {
        with(originalRequest.url.encodedPath) {
            if(contains("signin") || contains("login") || contains("nameCheck")) return false
        }
        return true
    }

    private fun buildAuthRequest(context: Context, originalRequest: Request) =
        if(isAuthNeeded(originalRequest)) originalRequest.newBuilder()
            .addHeader("Authorization", SharedPrefManager.getString(context) { JWT_ACCESS_BEARER })
            .addHeader("refreshToken", SharedPrefManager.getString(context) { REFRESH_TOKEN })
            .build()
        else originalRequest
}