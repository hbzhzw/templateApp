package io.github.hbzhzw.loger

import android.util.Log

object Loger {
    var isDebug: Boolean = false

    fun v(tag: String, msg: String?) {
        if (isDebug) {
            Log.v(tag, msg ?: "")
        }
    }

    fun d(tag: String, msg: String?) {
        if (isDebug) {
            Log.d(tag, msg ?: "")
        }
    }

    fun i(tag: String, msg: String?) {
        Log.i(tag, msg ?: "")
    }

    fun w(tag: String, msg: String?) {
        Log.w(tag, msg ?: "")
    }

    fun e(tag: String, msg: String?) {
        Log.e(tag, msg ?: "")
    }
}