package io.github.hbzhzw.utils.system

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.content.FileProvider
import io.github.hbzhzw.utils.AppContext
import java.io.File

object ApkUtil {
    val CONTENT_SCHEME = "content://"

    fun isInstalled(packageName: String?): Boolean {
        return packageName?.let {
            var packInfo: PackageInfo? = null
            try {
                packInfo = AppContext.packageMgr().getPackageInfo(it, 0)
            } catch (e: PackageManager.NameNotFoundException) {
                packInfo = null
            }
            packInfo != null
        } ?: false
    }

    fun install(context: Context, apkFile: String?) {
        apkFile?.let {
            if (!it.startsWith(CONTENT_SCHEME)) {
                FileProvider.getUriForFile(context,
                    fileProvAuthority(context), File(it))
            } else {
                parseAsUri(it)
            }?.also {
                Intent().apply {
                    setAction(Intent.ACTION_VIEW)
                    setDataAndType(it, "application/vnd.android.package-archive")
                    setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(this)
                }
            }
        }
    }

    private fun parseAsUri(uriStr: String?): Uri? {
        return uriStr?.let {
            Uri.parse(it)
        }
    }

    private fun fileProvAuthority(context: Context): String {
        return "${context.packageName}.fileProvider"
    }
}