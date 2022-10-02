package com.vside.app.feature.auth.data

sealed class VsideLoginType(val serverDataStr: String) {
    object Kakao: VsideLoginType("KAKAO")
    object Apple: VsideLoginType("APPLE")
}
