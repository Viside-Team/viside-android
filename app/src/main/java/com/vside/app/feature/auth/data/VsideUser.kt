package com.vside.app.feature.auth.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VsideUser(
    val nickname: String?,
    val email: String?,
    val loginType: String?,
    val gender: String?,
    val ageRange: String?,
    val snsId: String?
): Parcelable
