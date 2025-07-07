package com.app.dreamworld

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.app.dreamworld.databinding.ActivityLoginBinding
import com.app.dreamworld.databinding.ActivityMainBinding
import com.app.dreamworld.ui.auth.login.LoginViewModel
import com.app.dreamworld.ui.core.BaseVMBindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity :
    BaseVMBindingActivity<ActivityMainBinding, LoginViewModel>(LoginViewModel::class.java) {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBindingView(ActivityMainBinding.inflate(layoutInflater))


        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Set the status bar color
        window.statusBarColor = Color.BLACK

        // Set white icons (lightStatusBar = false)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
        if (Build.VERSION.SDK_INT > 34)
            // API level > 14
            // Your logic here
            {
            showToast("${Build.VERSION.SDK_INT}")
        // Apply top padding for system bar
            ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
                val statusBars = insets.getInsets(WindowInsetsCompat.Type.statusBars())

                view.setPadding(0, statusBars.top, 0, 0)
                insets
            }
    }
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,R.id.navigation_event,R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        supportActionBar?.hide()

    }
    fun hideBottom(){
        binding.navView.isVisible = false
    }
    fun showBottom(){
        binding.navView.isVisible = true
    }

    override fun attachObservers() {

    }

    override fun initComponents() {

    }

    override fun setClickListener() {

    }
}