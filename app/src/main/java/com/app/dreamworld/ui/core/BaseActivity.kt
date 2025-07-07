package com.app.dreamworld.ui.core

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.app.dreamworld.util.dialog.progress.ProgressDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Singleton


/**
 *  Activity used for Base of application
 */
@AndroidEntryPoint
@Singleton
abstract class BaseActivity : AppCompatActivity() {

    private val progressDialogFragment: ProgressDialogFragment by lazy { ProgressDialogFragment.newInstance() }
    private lateinit var baseApplication: BaseApplication
    private lateinit var currentLanguage: String
    var exitWithAnimation = true
    private var isActive = false
    var noInternetOnApp = false


    public override fun onStart() {
        super.onStart()
        isActive = true
    }

    /**
     * This method is used to change the status bar color
     *
     * @param drawable
     */
    fun setStatusBarGradiant(drawable: Drawable?) {
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
//        window.setBackgroundDrawable(drawable)
    }

    public override fun onStop() {
        isActive = false
        super.onStop()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseApplication = (application as BaseApplication)

    }

    /**
     * This method is used to show and hide progress dialog e.g. User have to pass true or false value
     *
     * @param isShow
     */
    fun showProgressDialog(isShow: Boolean) {
        if (isShow) {
            try {
                progressDialogFragment.dismissAllowingStateLoss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            progressDialogFragment.show(supportFragmentManager, ProgressDialogFragment.FRAGMENT_TAG)
        } else {
            try {
                progressDialogFragment.dismissAllowingStateLoss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * This method is used to handle more than clicks at time
     *
     * @param debounceTime
     * @param action
     */
    fun View.clickWithDebounce(debounceTime: Long = 700L, action: (view: View) -> Unit) {
        this.setOnClickListener(object : View.OnClickListener {
            private var lastClickTime: Long = 0
            override fun onClick(v: View) {
                if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
                else action(v)

                lastClickTime = SystemClock.elapsedRealtime()
            }
        })
    }
    /**
     * On network disconnected
     *
     */
    open fun onNetworkDisconnected() {
        startActivity(NoInternetActivity.createIntent(this))
    }

    /**
     * Show error message
     *
     * @param message
     */
    fun showErrorMessage(message: String) {
        //message.showSnackBar(this)
    }

    /**
     * Show success message
     *
     * @param message
     */
    fun showSuccessMessage(message: String) {
       // message.showSnackBar(this, SNACKBAR_TYPE_SUCCESS)
    }

    /**
     * Show toast
     *
     * @param message
     */
    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * This function is used to make activity ful screen
     *
     */
    fun makeFullScreen() {
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    /**
     * This function is used to toolbar
     *
     */
//    private val toolbarHelper by lazy { ToolbarHelper() }
//    private var customToolbarBinding: CustomToolbarBinding? = null
//    fun overrideToolbar(binding: CustomToolbarBinding, toolbarConfig: ToolbarConfig) {
//        this.customToolbarBinding = binding
//        toolbarHelper.setCustomToolbarBinding(binding)
//        toolbarHelper.setToolbarConfig(toolbarConfig)
//    }
    /**
     * This function is used to setStatusBarColor
     *
     */
    fun setStatusBarColor(@ColorInt color: Int) {
        window.statusBarColor = color
    }

}