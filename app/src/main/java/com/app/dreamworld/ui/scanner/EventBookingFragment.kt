package com.app.dreamworld.ui.scanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dreamworld.MainActivity
import com.app.dreamworld.R
import com.app.dreamworld.data.remote.res.EventBookingList
import com.app.dreamworld.databinding.FragmentScannerBinding
import com.app.dreamworld.ui.core.BaseVMBindingFragment
import com.app.dreamworld.ui.scanner.adapter.EventBookingAdapter

class EventBookingFragment :
    BaseVMBindingFragment<FragmentScannerBinding, EventBookingViewModel>(EventBookingViewModel::class.java) {

        private var eventBookingAdapter:EventBookingAdapter?=null
    private var eventBookingList:ArrayList<EventBookingList> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return generateBinding(FragmentScannerBinding.inflate(inflater), container)
    }
    override fun attachObservers() {
        viewModel.eventLiveData.observe(this){
            eventBookingList.clear()
            eventBookingList.addAll(it.eventBookingDetails)
            eventBookingAdapter?.notifyDataSetChanged()
            if (eventBookingList.isEmpty()){
                binding!!.tvNoData.isVisible=true
                binding!!.rvEventList.isVisible=false
            }else{
                binding!!.tvNoData.isVisible=false
                binding!!.rvEventList.isVisible=true
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isFirstTimeLoad){
            viewModel.callEventCustomerApi()
            initRecyclerView()
        }
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as MainActivity).showBottom()
    }


    override fun initComponents() {

    }

    override fun setClickListener() {
        binding?.apply {
            swRefresh.setOnRefreshListener {
                swRefresh.isRefreshing=false
                viewModel.callEventCustomerApi()
            }
        }

    }
    private fun initRecyclerView() {
        eventBookingAdapter = EventBookingAdapter(requireContext(), eventBookingList) { view, model, position ->
            when (view.id) {
                R.id.clMainBooking ->{
                    val action = EventBookingFragmentDirections.openMemberList(model)
                    findNavController().navigate(action)
                }
            }
        }
        binding!!.rvEventList.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        binding!!.rvEventList.adapter = eventBookingAdapter

    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}