package com.viside.app.feature.auth.data

import com.kakao.sdk.user.model.AgeRange

sealed class VisideAgeRange(val serverDataStr: String) {
    object Age0to9: VisideAgeRange("0~9")
    object Age10to14: VisideAgeRange("10~14")
    object Age15to19: VisideAgeRange("15~19")
    object Age20to29: VisideAgeRange("20~29")
    object Age30to39: VisideAgeRange("30~39")
    object Age40to49: VisideAgeRange("40~49")
    object Age50to59: VisideAgeRange("50~59")
    object Age60to69: VisideAgeRange("60~69")
    object Age70to79: VisideAgeRange("70~79")
    object Age80to89: VisideAgeRange("80~89")
    object Age90Above: VisideAgeRange("90~")
    companion object {
        fun getRequestStrByKakaoAgeRangeObj(kakaoAgeRangeObj : AgeRange?) : String? =
            when(kakaoAgeRangeObj) {
                AgeRange.AGE_0_9 -> Age0to9.serverDataStr
                AgeRange.AGE_10_14 -> Age10to14.serverDataStr
                AgeRange.AGE_15_19 -> Age15to19.serverDataStr
                AgeRange.AGE_20_29 -> Age20to29.serverDataStr
                AgeRange.AGE_30_39 -> Age30to39.serverDataStr
                AgeRange.AGE_40_49 -> Age40to49.serverDataStr
                AgeRange.AGE_50_59 -> Age50to59.serverDataStr
                AgeRange.AGE_60_69 -> Age60to69.serverDataStr
                AgeRange.AGE_70_79 -> Age70to79.serverDataStr
                AgeRange.AGE_80_89 -> Age80to89.serverDataStr
                AgeRange.AGE_90_ABOVE -> Age90Above.serverDataStr
                else -> null
            }
        
    }
}
