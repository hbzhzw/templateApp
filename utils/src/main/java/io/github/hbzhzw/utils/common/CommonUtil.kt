package io.github.hbzhzw.utils.common

import io.github.hbzhzw.loger.logE

object CommonUtil {
    const val TAG = "CommonUtil"

    fun optInt(str: String?, defInt: Int = 0): Int {
        var result = defInt
        try {
            result = str?.toInt() ?: defInt
        } catch (e: Exception) {
            logE(TAG) { "exception: $e" }
        }
        return result
    }

    fun optLong(str: String?, defLong: Long = 0L): Long {
        var result = defLong
        try {
            result = str?.toLong() ?: defLong
        } catch (e: Exception) {
            logE(TAG) { "exception: $e" }
        }
        return result
    }

    fun optFloat(str: String?, defFloat: Float = 0.0f): Float {
        var result = defFloat
        try {
            result = str?.toFloat() ?: defFloat
        } catch (e: Exception) {
            logE(TAG) { "exception: $e" }
        }
        return result
    }
}