package com.viside.app.util.log

import android.util.Log

object VisideLog {
    const val TAG = "VsideLog"
    fun d(msg: String, tr: Throwable? = null, tag: String = TAG) {
        Log.d(tag, msg, tr)
    }

    fun i(msg: String, tr: Throwable? = null, tag: String = TAG) {
        Log.i(tag, msg, tr)
    }

    fun w(msg: String, tr: Throwable? = null, tag: String = TAG) {
        Log.w(tag, msg, tr)
    }

    fun v(msg: String, tr: Throwable? = null, tag: String = TAG) {
        Log.v(tag, msg, tr)
    }

    fun e(msg: String, tr: Throwable? = null, tag: String = TAG) {
        Log.e(tag, msg, tr)
    }
}