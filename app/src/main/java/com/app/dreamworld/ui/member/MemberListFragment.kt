package com.app.dreamworld.ui.member

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dreamworld.MainActivity
import com.app.dreamworld.R
import com.app.dreamworld.data.remote.res.CustomerDetailsRes
import com.app.dreamworld.data.remote.res.EventBookingList
import com.app.dreamworld.databinding.FragmentMemberListBinding
import com.app.dreamworld.ui.core.BaseVMBindingFragment
import androidx.core.net.toUri


class MemberListFragment :
    BaseVMBindingFragment<FragmentMemberListBinding, MemberListViewModel>(MemberListViewModel::class.java) {

    val args: MemberListFragmentArgs by navArgs()
    private var memberListAdapter:MemberListAdapter?=null
    var event: EventBookingList?=null
    private var eventBookingList:ArrayList<CustomerDetailsRes> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return generateBinding(FragmentMemberListBinding.inflate(inflater), container)
    }

    override fun attachObservers() {
        viewModel.eventLiveData.observe(this){
            eventBookingList.clear()
            eventBookingList.addAll(it.customerDetails)
            memberListAdapter?.notifyDataSetChanged()
            if (eventBookingList.isEmpty()){
                binding!!.tvNoData.isVisible=true
                binding!!.rvRegisterCustomer.isVisible=false
            }else{
                binding!!.tvNoData.isVisible=false
                binding!!.rvRegisterCustomer.isVisible=true
            }
        }

    }

    override fun initComponents() {
         event = args.eventBooking
        setData(event)
        (requireActivity() as MainActivity).hideBottom()
        viewModel.callEventCustomerApi(event?.eventId.toString())
        initRecyclerView()
    }

    private fun setData(eventBookingList: EventBookingList?) {
        binding?.apply {
            tvEventName.text=eventBookingList?.eventTitle
            tvCustomeCount.text=(eventBookingList?.bookedSeats + " "+getString(R.string.registered_customers))
        }
    }

    override fun setClickListener() {
        binding?.apply {
            ivBack.clickWithDebounce {
                findNavController().popBackStack()
            }
            srlRegisterCustomer.setOnRefreshListener {
                srlRegisterCustomer.isRefreshing=false
                viewModel.callEventCustomerApi(event?.eventId.toString())
            }
        }
    }
    private fun initRecyclerView() {
        memberListAdapter = MemberListAdapter(requireContext(), eventBookingList) { view, model, position ->
            when (view.id) {
                R.id.ivShare -> {
                   val shareLink= "https://wa.me/+91${model.customerMobile}?text=https://ins.hrmsportal.co.in/events/event/api/ticket/index.php?number=" +model.bookingTicketNumber
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        shareLink.toUri())
                    startActivity (browserIntent)
                }
            }
        }
        binding!!.rvRegisterCustomer.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        binding!!.rvRegisterCustomer.adapter = memberListAdapter

    }

}