package com.viside.app.feature.auth.data

sealed class VisideLoginType(val serverDataStr: String) {
    object Kakao: VisideLoginType("KAKAO")
    object Apple: VisideLoginType("APPLE")
}
