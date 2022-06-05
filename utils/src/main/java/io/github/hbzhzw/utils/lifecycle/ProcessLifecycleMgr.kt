package io.github.hbzhzw.utils.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import io.github.hbzhzw.loger.Loger
import io.github.hbzhzw.utils.common.ListenerMgr

object ProcessLifecycleMgr: DefaultLifecycleObserver {
    private const val TAG = "ProcessLifecycleMgr"
    private val mListenMgr: ListenerMgr<IForegroudListener> = ListenerMgr()
    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

//    override fun onCreate(owner: LifecycleOwner) {
//        super.onCreate(owner)
//        Loger.i(TAG, "onCreate")
//    }

//    override fun onResume(owner: LifecycleOwner) {
//        super.onResume(owner)
//        Loger.i(TAG, "onResume")
//    }
//
//    override fun onPause(owner: LifecycleOwner) {
//        super.onPause(owner)
//        Loger.i(TAG, "onPause")
//    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Loger.i(TAG, "onStart")
        notifyForeground()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Loger.i(TAG, "onStop")
        notifyBackground()
    }

    fun addListener(listener: IForegroudListener?) {
        mListenMgr.addListener(listener)
    }

    fun removeListener(listener: IForegroudListener?): Boolean {
        return mListenMgr.removeListener(listener)
    }

    fun addHeadListener(listener: IForegroudListener?) {
        mListenMgr.addHeadListener(listener)
    }

    private fun notifyForeground() {
        mListenMgr.notifyListeners {
            it.onForeground()
        }
    }

    private fun notifyBackground() {
        mListenMgr.notifyListeners {
            it.onBackground()
        }
    }


    interface IForegroudListener {
        fun onForeground()
        fun onBackground()
    }
}