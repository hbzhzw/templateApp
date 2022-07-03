package io.github.hbzhzw.utils.system

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.os.Bundle
import android.provider.Settings
import io.github.hbzhzw.utils.AppContext

class SystemUtil {
    companion object {
        const val TAG = "SystemUtil"
        private var hasPrivacy = true
        private var VERSION_CODE: Long? = null
        private var VERSION_NAME: String? = null
        private var ANDROID_ID: String? = null
        private var UUID: String? = null

        @JvmStatic
        fun getVerCode(): Long {
            return VERSION_CODE ?: kotlin.run {
                val verCode = AppContext.context.packageManager?.let {
                    val packName = AppContext.packageName()
                    val packInfo: PackageInfo? = it.getPackageInfo(packName, 0)
                    packInfo?.let {
                        if (VersionUtil.hasP()) it.versionCode.toLong() else it.longVersionCode
                    } ?: -1L
                } ?: -1L
                VERSION_CODE = verCode
                verCode
            }
        }

        @JvmStatic
        fun getVerName(): String {
            return VERSION_NAME ?: kotlin.run {
                val verName = AppContext.context.packageManager?.let {
                    val packName = AppContext.packageName()
                    val packInfo: PackageInfo? = it.getPackageInfo(packName, 0)
                    packInfo?.versionName ?: ""
                } ?: ""
                VERSION_NAME = verName
                verName
            }
        }

        @JvmStatic
        fun getMetaData(key: String): String? {
            return AppContext.context.packageManager?.let {
                val appInfo = it.getApplicationInfo(AppContext.packageName(), 0)
                val metaBundle: Bundle? = appInfo.metaData
                metaBundle?.getString(key)
            }
        }

        @JvmStatic
        @SuppressLint("HardwareIds")
        fun getAndroidId(): String? {
            if (ANDROID_ID == null && hasPrivacy) {
                synchronized(SystemUtil::class.java) {
                    if (ANDROID_ID == null) {
                        ANDROID_ID = Settings.Secure.getString(AppContext.contentResolver(),
                            Settings.Secure.ANDROID_ID)
                    }
                }
            }
            return ANDROID_ID
        }

        @JvmStatic
        fun getUUID(): String {
            if (UUID == null) {
                synchronized(SystemUtil::class.java) {
                    if (UUID == null) {
                        UUID = java.util.UUID.fromString(
                            System.currentTimeMillis().toString()).toString()
                    }
                }
            }
            return UUID ?: ""
        }
    }
}