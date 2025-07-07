
package com.app.dreamworld.ui.eventDetails.bottom

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dreamworld.R
import com.app.dreamworld.databinding.FragmentSearchBottomSheetBinding
import com.app.dreamworld.ui.auth.login.LoginViewModel
import com.app.dreamworld.ui.core.BaseVMBottomSheetDialogFragment
import com.app.dreamworld.ui.eventDetails.EventViewModel
import com.app.dreamworld.util.clickWithDebounce
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


/**
 * A simple [Fragment] subclass.
 * Use the [SearchBottomSheetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchBottomSheetFragment : BaseVMBottomSheetDialogFragment<FragmentSearchBottomSheetBinding, SearchViewMode>(SearchViewMode::class.java) {
    var SCREEN_HEIGHT = 0


    var sendClickListener: (code: String) -> Unit = {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme2)
    }

    companion object {

        fun newInstance() = SearchBottomSheetFragment().apply {
            this.arguments = Bundle().apply {

            }
        }
    }

    override fun onStart() {
        super.onStart()
        //setDialog()
    }

    private fun setDialog() {
        if (dialog != null) {
            val height = (SCREEN_HEIGHT * 0.30).toInt()
            dialog?.window?.setGravity(Gravity.BOTTOM)

            dialog?.setCanceledOnTouchOutside(arguments?.getBoolean("canceledOnTouchOutside", true)!!)
            val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheet!!).peekHeight = height
            BottomSheetBehavior.from(bottomSheet).peekHeight = height
            val layoutParams = bottomSheet.layoutParams
            if (layoutParams != null) {
                layoutParams.height = (SCREEN_HEIGHT * 0.30).toInt()
            }
            bottomSheet.layoutParams = layoutParams
            dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
            val behavior: BottomSheetBehavior<View> = BottomSheetBehavior.from(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState > BottomSheetBehavior.STATE_DRAGGING) bottomSheet.post { behavior.state = BottomSheetBehavior.STATE_EXPANDED }
                }
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.post {
            val bottomSheet = (dialog as BottomSheetDialog).findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            BottomSheetBehavior.from<View>(bottomSheet!!).apply {
                state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        initComponent()
        setListeners()
    }

    private fun initComponent() {

    }



    private fun setListeners() {
        binding.btnSearch.clickWithDebounce {
            sendClickListener(binding.edtSearch.text.toString())
            dialog?.dismiss()
        }
    }


}