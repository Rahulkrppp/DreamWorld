package com.app.dreamworld.ui.core

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.HiltAndroidApp
import com.app.dreamworld.data.remote.ApiService
import com.app.dreamworld.util.preference.EasyPref


/**
 * Base application
 *
 * @constructor Create empty Base application
 */
@HiltAndroidApp
class BaseApplication : Application(), LifecycleEventObserver {

    lateinit var lifecycle: Lifecycle
    val apiService: ApiService?=null

    companion object {

        lateinit var application: BaseApplication
        lateinit var sharedPreference: EasyPref
        var themeValue = 0
        var scannerFile : String= ""
        var scannerThreeStepFile : String= ""
        var isAppInForeground: Boolean = false
//        var isUnreadNotificationAvailable = false
        var notificationCount : MutableLiveData<Int> = MutableLiveData()

        //we want only MainApplication can set the instance value


    }
    /**
     * initializes all the instances used globally in app
     *
     */
    override fun onCreate() {
        super.onCreate()
        application = this
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        sharedPreference = EasyPref.createAppPref(this)
//        if (getDefaultTheme(this) == Configuration.UI_MODE_NIGHT_YES) {
//            setTheme(R.style.Theme_Mobility_Dark)
//        } else {
//            setTheme(R.style.Theme_Mobility_Light)
//        }

    }
    fun getDefaultTheme(context: Context): Int {
        return when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> Configuration.UI_MODE_NIGHT_YES
            Configuration.UI_MODE_NIGHT_NO -> Configuration.UI_MODE_NIGHT_NO
            else -> Configuration.UI_MODE_NIGHT_UNDEFINED
        }
    }


    /**
     * handles state changes
     *
     * @param source
     * @param event
     */
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> {
                isAppInForeground = true
            }

            Lifecycle.Event.ON_RESUME -> {
                isAppInForeground = true
            }

            Lifecycle.Event.ON_PAUSE -> {
                isAppInForeground = false
            }

            Lifecycle.Event.ON_STOP -> {
                isAppInForeground = false
            }

            Lifecycle.Event.ON_DESTROY -> {
                isAppInForeground = false
            }

            else -> {}
        }
    }


}