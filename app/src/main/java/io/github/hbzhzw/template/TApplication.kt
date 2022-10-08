package io.github.hbzhzw.template

import android.app.Application
import android.content.Context
import io.github.hbzhzw.loger.logI
import io.github.hbzhzw.utils.AppContext
import io.github.hbzhzw.utils.lifecycle.ProcessLifecycleMgr

class TApplication: Application() {
//    override fun attachBaseContext(base: Context?) {
//        super.attachBaseContext(base)
//    }

    override fun onCreate() {
        super.onCreate()
        AppContext.init(this)
        ProcessLifecycleMgr.addListener(object: ProcessLifecycleMgr.IForegroudListener {
            override fun onForeground() {
                logI(TAG) { "onForeground" }
            }

            override fun onBackground() {
                logI(TAG) { "onBackground" }
            }
        })
    }

    companion object {
        const val TAG = "TApplication"
    }
}