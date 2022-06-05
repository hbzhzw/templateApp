package io.github.hbzhzw.utils.system

import android.os.Build

class VersionUtil {
    companion object {
        fun hasTiramisu(): Boolean {
            return Build.VERSION.SDK_INT >= 33
        }

        fun hasNougat(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
        }

        fun hasP(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
        }
    }
}