package com.vside.app.util.common.sharedpref

import android.content.Context
import android.content.SharedPreferences

internal object SharedPrefManager {
    /** preference를 가져올 때 사용할 이름 **/
    private const val PREFERENCE_NAME = "VsideSharedPreference"

    /** 키 값에 해당하는 value값이 없을 때 반환할 기본 값 **/
    const val DEFAULT_VALUE_STRING=""
    const val DEFAULT_VALUE_BOOLEAN= false
    const val DEFAULT_VALUE_INT=-1
    const val DEFAULT_VALUE_LONG=-1L
    const val DEFAULT_VALUE_FLOAT=-1F

    /** preference 객체 반환 **/
    private fun getPreferences(context: Context): SharedPreferences = context.getSharedPreferences(
        PREFERENCE_NAME, Context.MODE_PRIVATE)

    /** String 값 게터, 세터 **/
    fun getString(context: Context, key: StringKey.() -> String): String {
        val prefs = getPreferences(context)
        return prefs.getString(key(StringKey), DEFAULT_VALUE_STRING) ?: DEFAULT_VALUE_STRING
    }
    fun setString(context: Context, key: StringKey.() -> String, value: String) {
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putString(key(StringKey), value)
        editor.apply()
    }
    fun removeString(context: Context, key: StringKey.() -> String) {
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putString(key(StringKey), DEFAULT_VALUE_STRING)
        editor.apply()
    }

    internal object StringKey {
        const val TOKEN_BEARER = "TOKEN_BEARER"
    }

    /** Boolean 값 게터, 세터 **/
    fun getBoolean(context: Context, key: BooleanKey.() -> String): Boolean{
        val prefs = getPreferences(context)
        return prefs.getBoolean(key(BooleanKey), DEFAULT_VALUE_BOOLEAN)
    }
    fun setBoolean(context: Context, key: BooleanKey.() -> String, value: Boolean) {
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putBoolean(key(BooleanKey), value)
        editor.apply()
    }
    fun removeBoolean(context: Context, key: BooleanKey.() -> String) {
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putBoolean(key(BooleanKey), DEFAULT_VALUE_BOOLEAN)
        editor.apply()
    }

    internal object BooleanKey {
        const val IS_LOGGED_IN = "IS_LOGGED_IN"
        const val HAVE_SEEN_WELCOME_TOAST = "HAVE_SEEN_WELCOME_TOAST"
    }

    /** Int 값 게터, 세터 **/
    fun getInt(context: Context, key: IntKey.() -> String): Int {
        val prefs = getPreferences(context)
        return prefs.getInt(key(IntKey), DEFAULT_VALUE_INT)
    }
    fun setInt(context: Context, key: IntKey.() -> String, value:Int) {
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putInt(key(IntKey), value)
        editor.apply()
    }
    fun removeInt(context: Context, key: IntKey.() -> String) {
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putInt(key(IntKey), DEFAULT_VALUE_INT)
        editor.apply()
    }

    internal object IntKey {

    }

    /** Long 값 게터, 세터 **/
    fun getLong(context: Context, key: LongKey.() -> String): Long {
        val prefs = getPreferences(context)
        return prefs.getLong(key(LongKey), DEFAULT_VALUE_LONG)
    }
    fun setLong(context: Context, key: LongKey.() -> String, value:Long) {
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putLong(key(LongKey), value)
        editor.apply()
    }
    fun removeLong(context: Context, key: LongKey.() -> String){
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putLong(key(LongKey), DEFAULT_VALUE_LONG)
        editor.apply()
    }

    internal object LongKey {

    }

    /** Float 값 게터, 세터 **/
    fun getFloat(context: Context, key: FloatKey.() -> String): Float {
        val prefs = getPreferences(context)
        return prefs.getFloat(key(FloatKey), DEFAULT_VALUE_FLOAT)
    }
    fun setFloat(context: Context, key: FloatKey.() -> String, value:Float) {
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putFloat(key(FloatKey), value)
        editor.apply()
    }
    fun removeFloat(context: Context, key: FloatKey.() -> String){
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putFloat(key(FloatKey), DEFAULT_VALUE_FLOAT)
        editor.apply()
    }

    internal object FloatKey {

    }
}