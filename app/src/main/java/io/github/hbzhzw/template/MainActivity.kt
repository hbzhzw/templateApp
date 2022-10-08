package io.github.hbzhzw.template

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import io.github.hbzhzw.loger.Loger
import io.github.hbzhzw.template.databinding.ActivityMainBinding
import io.github.hbzhzw.utils.AppContext
import io.github.hbzhzw.utils.lifecycle.ProcessLifecycleMgr
import io.github.hbzhzw.utils.system.SystemUtil

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        dumpSysInfo()
    }


    private fun dumpSysInfo() {
        Loger.i(TAG, "versionName: ${SystemUtil.getVerName()}, versionCode: ${SystemUtil.getVerCode()}")
    }

    companion object {
        const val TAG = "MainActivity"
    }
}