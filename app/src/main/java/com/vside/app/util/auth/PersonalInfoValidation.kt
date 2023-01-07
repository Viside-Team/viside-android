package com.vside.app.util.auth

import java.util.regex.Pattern

object PersonalInfoValidation {
    private const val NICKNAME_MIN_LENGTH = 1
    private const val NICKNAME_MAX_LENGTH = 10

    private const val NICKNAME_REGEX =
        "^([A-Za-z\\d가-힣])[A-Za-z\\d가-힣\\s]{${NICKNAME_MIN_LENGTH - 1},${NICKNAME_MAX_LENGTH - 1}}$"

    private const val MSG_NICKNAME_UNACCEPTED_CHAR = "한글, 영어, 숫자, 공백만 입력해주세요."
    private const val MSG_NICKNAME_TOO_SHORT = "${NICKNAME_MIN_LENGTH}글자 이상 입력해주세요."
    private const val MSG_NICKNAME_TOO_LONG = "${NICKNAME_MAX_LENGTH}글자 이하로 입력해주세요."
    private const val MSG_NICKNAME_VALIDATE = "사용가능한 닉네임이에요!"
    private const val MSG_NICKNAME_CONSONANT_VOWEL_KOREAN = "자음, 모음이 아닌 글자로 입력해주세요."
    const val MSG_NICKNAME_DUPLICATED = "중복된 닉네임이에요."

    private val nicknameMatcher by lazy {
        Pattern.compile(NICKNAME_REGEX).matcher("")
    }

    fun nicknameValidationCheck(nickname: String): Boolean {
        nicknameMatcher.reset(nickname)
        return nicknameMatcher.matches()
    }

    fun nicknameGuidanceStr(nickname: String?): String {
        if (nickname.isNullOrEmpty()) return ""
        if (nickname.length < NICKNAME_MIN_LENGTH) return MSG_NICKNAME_TOO_SHORT
        if (nickname.length > NICKNAME_MAX_LENGTH) return MSG_NICKNAME_TOO_LONG
        if ("[ㄱ-ㅎ|ㅏ-ㅣ]".toRegex().containsMatchIn(nickname)) return MSG_NICKNAME_CONSONANT_VOWEL_KOREAN
        if (!nicknameValidationCheck(nickname)) return MSG_NICKNAME_UNACCEPTED_CHAR
        return MSG_NICKNAME_VALIDATE
    }
}