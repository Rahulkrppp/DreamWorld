package com.app.dreamworld.ui.dashboard.bottom

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dreamworld.R
import com.app.dreamworld.data.remote.res.ShowsDetailsRes
import com.app.dreamworld.databinding.FragmentSearchBottomSheetBinding
import com.app.dreamworld.databinding.FragmentSelectShowBottomSheetBinding
import com.app.dreamworld.ui.core.BaseVMBottomSheetDialogFragment
import com.app.dreamworld.ui.dashboard.adapter.EventAdapter
import com.app.dreamworld.ui.dashboard.adapter.ShowTimeAdapter
import com.app.dreamworld.ui.eventDetails.bottom.SearchBottomSheetFragment
import com.app.dreamworld.ui.eventDetails.bottom.SearchViewMode
import com.app.dreamworld.ui.home.HomeFragmentDirections
import com.app.dreamworld.util.clickWithDebounce
import com.app.dreamworld.util.parcelableArrayList
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


class SelectShowBottomSheetFragment : BaseVMBottomSheetDialogFragment<FragmentSelectShowBottomSheetBinding, SearchViewMode>(SearchViewMode::class.java) {
    var SCREEN_HEIGHT = 0

        var showTimeAdapter:ShowTimeAdapter?=null
    var sendClickListener: (model: ShowsDetailsRes) -> Unit = {}
    var showsDetailsList:ArrayList<ShowsDetailsRes> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSelectShowBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme2)
    }

    companion object {
        const val SHOWLIST="showList"
        fun newInstance(showsDetailsList:ArrayList<ShowsDetailsRes> = arrayListOf()) = SelectShowBottomSheetFragment().apply {
            this.arguments = Bundle().apply {
                putParcelableArrayList(SHOWLIST,showsDetailsList)
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
        initRecyclerView()
        showsDetailsList.clear()
        if (arguments!=null) {
            showsDetailsList.addAll(arguments?.parcelableArrayList(SHOWLIST)!!)
            showTimeAdapter?.notifyDataSetChanged()
        }
    }



    private fun setListeners() {

    }
    private fun initRecyclerView() {
        showTimeAdapter = ShowTimeAdapter(requireContext(), showsDetailsList) { view, model, position ->
            when (view.id) {
                R.id .clMainShow ->{
                    sendClickListener(model)
                    dialog?.dismiss()
                }
            }
        }
        binding!!.rvShowTime.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        binding!!.rvShowTime.adapter = showTimeAdapter

    }

}