package com.app.dreamworld.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.dreamworld.MainActivity
import com.app.dreamworld.data.remote.res.User
import com.app.dreamworld.databinding.ActivitySplashBinding
import com.app.dreamworld.ui.auth.login.Login
import com.app.dreamworld.ui.core.BaseApplication
import com.app.dreamworld.util.extension.isLoggedIn
import com.app.dreamworld.util.preference.EasyPref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Splash:AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            if (isLoggedIn()) {
                startActivity(Intent(this@Splash, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))
                finish()
            }else {
                startActivity(
                    Intent(
                        this@Splash,
                        Login::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }
            finish()
        }
    }


}