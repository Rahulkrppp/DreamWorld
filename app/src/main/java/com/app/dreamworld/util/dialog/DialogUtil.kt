package com.app.dreamworld.util.dialog

import android.annotation.SuppressLint
import android.app.*
import android.content.res.Configuration
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.*
import com.app.dreamworld.databinding.DialogCommonLayoutBinding
import com.app.dreamworld.util.helper.DialogFragmentHelper

import java.util.*


/**
 * This class contain alert message helper
 */
object DialogUtil {
    var singleAlertDialog: AlertDialog? = null

    interface IL {
        fun onSuccess()

        fun onCancel(isNeutral: Boolean)
    }

    /**
     * This method is used to show alert dialog box for force close application
     *
     * @param context - Object of Context, appContext from where the activity is going
     * to start.
     * @param msg     - Message String that represents alert box message
     */


    private fun fromHtml(html: String): Spanned {
        return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    }


    fun showDialog(supportFragmentManager: androidx.fragment.app.FragmentManager, title: String? = null, message: String? = null, positiveButtonText: String? = null, negativeButtonText: String? = null, il: IL? = null, image: Int? = null, isCancelShow: Boolean = false, isTitleShow: Int = View.VISIBLE, isMsgShow: Int = View.VISIBLE) {

        DialogFragmentHelper.with(inflate = DialogCommonLayoutBinding::inflate, isCancellable = false) { binding, context, dialog ->

            with(binding) {

                if (title.isNullOrEmpty()) {
                    tvTitle.visibility = View.GONE
                } else {
                    tvTitle.visibility = View.VISIBLE
                    tvTitle.text = title
                }

                if (message.isNullOrEmpty()) {
                    tvMessage.visibility = View.GONE
                } else {
                    tvMessage.visibility = View.VISIBLE
                    tvMessage.text = message
                }

                if (!positiveButtonText.isNullOrEmpty() && !negativeButtonText.isNullOrEmpty()) {
                    tvYes.text = positiveButtonText
                    tvNo.text = negativeButtonText

                    tvYes.visibility = View.VISIBLE
                    tvNo.visibility = View.VISIBLE
                    tvOk.visibility = View.GONE
                } else {
                    if (!positiveButtonText.isNullOrEmpty()) {
                        tvOk.text = positiveButtonText
                    }

                    tvYes.visibility = View.GONE
                    tvNo.visibility = View.GONE
                    tvOk.visibility = View.VISIBLE
                }

                if (image == null) {
                    ivImage.visibility = View.GONE
                } else {
                    ivImage.visibility = View.VISIBLE
                    ivImage.setImageResource(image)
                }

                if (isCancelShow) {
                    ivClose.visibility = View.VISIBLE
                } else {
                    ivClose.visibility = View.GONE
                }
                tvTitle.visibility = isTitleShow
                tvMessage.visibility = isMsgShow
                tvYes.setOnClickListener {
                    il?.onSuccess()
                    dialog.dismiss()
                }
                tvNo.setOnClickListener {
                    il?.onCancel(false)
                    dialog.dismiss()
                }
                tvOk.setOnClickListener {
                    il?.onSuccess()
                    dialog.dismiss()
                }
                ivClose.setOnClickListener {
                    il?.onCancel(true)
                    dialog.dismiss()
                }


            }

        }.apply { show(supportFragmentManager, "alert_dialog_token_expire") }

    }


    @SuppressLint("ClickableViewAccessibility")
    fun Dialog?.addTouchEvent() {
        this?.window?.decorView?.setOnTouchListener { _, motionEvent ->
            Log.e("Touch", "onUserInteraction addTouchEvent")

            if (motionEvent?.actionMasked == MotionEvent.ACTION_UP) {
                //EventBus.getDefault().post(TouchInfoEvent())
            }
            false
        }
    }
}
