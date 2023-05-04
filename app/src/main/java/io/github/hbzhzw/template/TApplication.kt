package io.github.hbzhzw.template

import android.app.Application
import android.content.Context
import android.os.IBinder
import io.github.hbzhzw.loger.logI
import io.github.hbzhzw.utils.AppContext
import io.github.hbzhzw.utils.lifecycle.ProcessLifecycleMgr

class TApplication: Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

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

        object: IRemoteService {
            override fun asBinder(): IBinder {
                TODO("Not yet implemented")
            }

            override fun getPid(): Int {
                TODO("Not yet implemented")
            }

            override fun basicTypes(
                anInt: Int,
                aLong: Long,
                aBoolean: Boolean,
                aFloat: Float,
                aDouble: Double,
                aString: String?
            ) {
                TODO("Not yet implemented")
            }

        }
    }

    companion object {
        const val TAG = "TApplication"
    }
}