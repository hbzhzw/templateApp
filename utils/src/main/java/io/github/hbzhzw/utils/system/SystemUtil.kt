package io.github.hbzhzw.utils.system

import android.content.Context
import android.content.pm.PackageInfo
import android.os.Bundle
import io.github.hbzhzw.utils.AppContext

class SystemUtil {
    companion object {
        const val TAG = "SystemUtil"
        private var VERSION_CODE: Long? = null
        private var VERSION_NAME: String? = null

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

        fun getMetaData(key: String): String? {
            return AppContext.context.packageManager?.let {
                val appInfo = it.getApplicationInfo(AppContext.packageName(), 0)
                val metaBundle: Bundle? = appInfo.metaData
                metaBundle?.getString(key)
            }
        }
    }
}