package io.github.hbzhzw.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import io.github.hbzhzw.utils.lifecycle.ActivityMgr

@SuppressLint("StaticFieldLeak")
object AppContext {
    lateinit var context: Context

    /**
     * The method should be called when application created
     */
    @JvmStatic
    fun init(application: Application) {
        context = application.applicationContext
        application.registerActivityLifecycleCallbacks(ActivityMgr)
    }

    @JvmStatic
    fun packageName(): String {
        return context.packageName
    }

    fun service(svsName: String): Any? {
        return context.getSystemService(svsName)
    }


    fun drawable(id: Int): Drawable? {
        return ResourcesCompat.getDrawable(context.resources, id, null)
    }

    fun dimenPixelSize(id: Int): Int {
        return context.resources.getDimensionPixelSize(id)
    }

    fun startActivity(intent: Intent) {
        intent.apply {
            setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            setAction(Intent.ACTION_MAIN)
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        context.startActivity(intent)
    }

    fun contentResolver():  ContentResolver {
        return context.contentResolver
    }
}