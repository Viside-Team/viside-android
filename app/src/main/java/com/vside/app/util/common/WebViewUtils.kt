package com.vside.app.util.common

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.FragmentActivity
import com.vside.app.util.log.VsideLog

@SuppressLint("SetJavaScriptEnabled")
fun webViewSetting(fragmentActivity: FragmentActivity, webView: WebView, progressBar: View, url: String, resultLauncher: ActivityResultLauncher<Intent>? = null) {
    /** url 을 받아 처리하는 웹뷰 클라이언트 **/
    class VsideWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            // 요청이 null 이 아닐 경우만 처리
            if (request != null) {
                val backUri = Uri.parse(request.url.toString())
                VsideLog.d("uri $backUri")
                VsideLog.d("scheme ${backUri.scheme}")
                VsideLog.d("host ${backUri.host}")
                VsideLog.d("path ${backUri.path}")
                VsideLog.d("query ${backUri.query}")

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(request.url.toString()))
                fragmentActivity.startActivity(intent)
            }
            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progressBar.visibility = View.INVISIBLE
        }
    }

    webView.settings.javaScriptEnabled = true
    webView.settings.useWideViewPort = true
    webView.settings.domStorageEnabled = true

    webView.settings.loadWithOverviewMode = true

    webView.webViewClient = VsideWebViewClient()
    VsideLog.d("url $url")
    webView.loadUrl(url)
}