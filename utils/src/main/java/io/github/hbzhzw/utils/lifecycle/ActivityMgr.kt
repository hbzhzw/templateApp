package io.github.hbzhzw.utils.lifecycle

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import io.github.hbzhzw.utils.AppContext
import io.github.hbzhzw.utils.common.ListenerMgr
import java.io.Serializable
import java.util.*

object ActivityMgr: Application.ActivityLifecycleCallbacks {
    private val mActivities = Stack<Activity>()
    private val mListeners = ListenerMgr<IActivityListener>()
    private var mWxEntryCls: Class<Any>? = null
    private var mTransferCls: Class<Any>? = null
    private val mLauncherCls: Class<Any>? = null

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        pushActivity(activity)
        mListeners.notifyListeners {
            it.onActivityCreated(activity)
        }
    }

    override fun onActivityStarted(activity: Activity) {
        mListeners.notifyListeners {
            it.onActivityStartd(activity)
        }
    }

    override fun onActivityResumed(activity: Activity) {
        mListeners.notifyListeners {
            it.onActivityResumed(activity)
        }
    }

    override fun onActivityPaused(activity: Activity) {
        mListeners.notifyListeners {
            it.onActivityPaused(activity)
        }
    }

    override fun onActivityStopped(activity: Activity) {
        mListeners.notifyListeners {
            it.onActivityStopped(activity)
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        mListeners.notifyListeners {
            it.onActivitySaveInstanceState(activity, outState)
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
        mListeners.notifyListeners {
            it.onActivityDestroyed(activity)
        }
        popActivity(activity)
    }

    fun addActivityListener(listener: IActivityListener?) {
        mListeners.addListener(listener)
    }

    fun removeActivityListener(listener: IActivityListener?) {
        mListeners.removeListener(listener)
    }

    fun bringAppFront(key: String? = null, param: Any? = null) {
        var isConsumed = false
        val size = activitySize()
        if (size > 0) {
            for (i in (size - 1) downTo  0) {
                val activity = mActivities.get(i)
                if (!isWxActivity(activity) && !isTransferActivity(activity)) {
                    val activityManager = AppContext.service(Context.ACTIVITY_SERVICE) as? ActivityManager
                    activityManager?.let {
                        it.moveTaskToFront(activity.taskId, ActivityManager.MOVE_TASK_WITH_HOME)
                        isConsumed = true
                    }
                    break
                }
            }
        }

        if (!isConsumed) {
            startLauncherActivity(key, param)
        }
    }

    fun startLauncherActivity(key: String? = null, param: Any? = null): Boolean {
        var isSuccess: Boolean = false
        mLauncherCls?.let {
            val intent: Intent = Intent(AppContext.context, it).apply {
                key?.let {
                    (param as? Serializable)?.apply {
                        putExtra(it, this)
                    }
                }
            }
            AppContext.startActivity(intent)
            isSuccess = true
        }
        return isSuccess
    }

    fun topActivity(): Activity? {
        return topActivity(true)
    }

    fun topActivity(skipFinishing: Boolean): Activity? {
        var resultAct: Activity? = null
        val size = activitySize()
        if (size > 0) {
            for (i in (size - 1)  downTo 0) {
                val activity = mActivities.get(i)
                if (skipFinishing) {
                    if (activity.isFinishing) {
                        continue
                    } else {
                        resultAct = activity
                        break
                    }
                } else {
                    resultAct = activity
                    break
                }
            }
        }
        return resultAct
    }

    fun prevActivity(): Activity? {
        var resultAct: Activity? = null
        val size = activitySize()
        if (size > 1) {
            for (i in (size - 1) downTo 0) {
                val activity = mActivities.get(i)
                if (activity.isFinishing) {
                    continue
                } else if (i > 0) {
                    resultAct = mActivities.get(i - 1)
                    break
                }
            }
        }
        return resultAct
    }

    fun activitySize(): Int {
        return mActivities.size
    }

    private fun pushActivity(activity: Activity) {
        mActivities.push(activity)
    }

    private fun popActivity(activity: Activity) {
        mActivities.remove(activity)
    }

    private fun activityAtIdx(idx: Int): Activity? {
        val size = activitySize()
        return if (size > idx) mActivities.get(idx) else null
    }

    private fun isWxActivity(activity: Activity): Boolean {
        return activity::class.java.canonicalName?.equals(wxActivityName()) ?: false
    }

    private fun isTransferActivity(activity: Activity): Boolean {
        return activity::class.java.canonicalName?.equals(transferActivityName()) ?: false
    }

    private fun wxActivityName(): String? {
        return mWxEntryCls?.canonicalName
    }

    private fun transferActivityName(): String? {
        return mTransferCls?.canonicalName
    }

    interface IActivityListener {
        fun onActivityCreated(activity: Activity)
        fun onActivityStartd(activity: Activity)
        fun onActivityResumed(activity: Activity)
        fun onActivityPaused(activity: Activity)
        fun onActivityStopped(activity: Activity)
        fun onActivitySaveInstanceState(activity: Activity, outState: Bundle)
        fun onActivityDestroyed(activity: Activity)
    }
}