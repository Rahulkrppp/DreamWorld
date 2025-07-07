package com.app.dreamworld.ui.core

import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.dreamworld.util.dialog.progress.ProgressDialogFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * This class contains common things used in fragment
 *
 */
@AndroidEntryPoint
abstract class BaseFragment : Fragment() {

    /**
     * Used when we don't want to create/inflate view if same fragment instance is used.
     */
    var isFirstTimeLoad: Boolean = false
    private var mFragmentNavigation: FragmentNavigation? = null
    lateinit var baseActivity: BaseActivity
    private val progressDialogFragment: ProgressDialogFragment by lazy { ProgressDialogFragment.newInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onLoad()
    }
    /*   override fun onStart() {
           super.onStart()
           EventBus.getDefault().register(this)
       }

       override fun onStop() {
           super.onStop()
           EventBus.getDefault().unregister(this)
       }*/
    /**
     * On load
     *
     */
    open fun onLoad() {

    }

    /**
     * On network disconnected
     *
     */
    open fun onNetworkDisconnected() {
        startActivity(NoInternetActivity.createIntent(requireContext()))
    }

    /**
     * Show error message
     *
     * @param message
     */
    fun showErrorMessage(message: String) {
        //message.showSnackBar(requireActivity())
    }

    /**
     * Show success message
     *
     * @param message
     */
    fun showSuccessMessage(message: String) {
        //message.showSnackBar(requireActivity(), SNACKBAR_TYPE_SUCCESS)
    }

    /**
     * Show success message
     *
     * @param message
     */
    fun showAlertMessage(message: String) {
        // message.showSnackBar(requireActivity(), SNACKBAR_TYPE_ALERT)
    }

    /**
     * Show toast
     *
     * @param message
     */
    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    /**
     * This interface is used for push and pop fragments in the stack
     *
     */
    interface FragmentNavigation {
        fun pushFragment(fragment: Fragment)

        fun popFragment(depth: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNavigation) {
            mFragmentNavigation = context
        }
        baseActivity = context as BaseActivity
    }

    /**
     * This method is used to push fragment on stack
     *
     * @param fragment
     */
    fun pushFragment(fragment: Fragment) {
        if (mFragmentNavigation != null) {
            mFragmentNavigation?.pushFragment(fragment)
        }
    }

    /**
     *
     * This fragment is used to pop fragment form the stack
     *
     * @param depth
     */
    fun popFragment(depth: Int = 1) {
        if (mFragmentNavigation != null) {
            mFragmentNavigation?.popFragment(depth)
        }
    }

    open fun onBackPressed(): Boolean {

        return false
    }


    /**
     *
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
     * This method is used to shoe and hide progress dialog
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
            progressDialogFragment.show(
                activity?.supportFragmentManager!!,
                ProgressDialogFragment.FRAGMENT_TAG
            )
        } else {
            try {
                progressDialogFragment.dismissAllowingStateLoss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /* private val toolbarHelper by lazy { ToolbarHelper() }
     private var customToolbarBinding: CustomToolbarBinding? = null
     fun overrideToolbar(binding: CustomToolbarBinding, toolbarConfig: ToolbarConfig) {
         this.customToolbarBinding = binding
         toolbarHelper.setCustomToolbarBinding(binding)
         toolbarHelper.setToolbarConfig(toolbarConfig)
         setThemeForNotificationIcon()
     }*/

}