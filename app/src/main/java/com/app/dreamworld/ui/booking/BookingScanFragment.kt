package com.app.dreamworld.ui.booking

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dreamworld.MainActivity
import com.app.dreamworld.R
import com.app.dreamworld.data.remote.res.TicketBookingDetailsReq
import com.app.dreamworld.databinding.FragmentBookingScanBinding
import com.app.dreamworld.databinding.FragmentEventDetailBinding
import com.app.dreamworld.databinding.FragmentScannerBinding
import com.app.dreamworld.ui.core.BaseVMBindingFragment
import com.app.dreamworld.ui.eventDetails.EventDetailFragmentArgs
import com.app.dreamworld.ui.eventDetails.EventViewModel
import com.app.dreamworld.util.loadImage
import com.app.dreamworld.BuildConfig
import com.app.dreamworld.ui.booking.adapter.TicketScanBookingAapter
import com.app.dreamworld.ui.scanner.EventBookingFragmentDirections
import com.app.dreamworld.ui.scanner.adapter.EventBookingAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.log


class BookingScanFragment :
    BaseVMBindingFragment<FragmentBookingScanBinding, EventViewModel>(EventViewModel::class.java) {
    val args: BookingScanFragmentArgs by navArgs()
     private var ticketBookingDetailsReq:ArrayList<TicketBookingDetailsReq> = arrayListOf()
    private var ticketScanBookingAapter:TicketScanBookingAapter?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return generateBinding(FragmentBookingScanBinding.inflate(inflater), container)
    }
    override fun attachObservers() {
        viewModel.scanTicketLiveData.observe(this){


                ticketBookingDetailsReq.clear()
                ticketBookingDetailsReq.addAll(it.ticketBookingDetails)
                ticketBookingDetailsReq.forEachIndexed { index, ticketBookingDetailsReq ->
                    ticketBookingDetailsReq.selected = true
                }
                Log.e("", "attachObservers===========:${it.ticketBookingDetails} ",)
                ticketScanBookingAapter?.notifyDataSetChanged()
                setData()
            if (ticketBookingDetailsReq.isNotEmpty()) {
                binding!!.tvNoData.isVisible=false
                binding!!.llMain.isVisible=true
            }else{
                binding!!.tvNoData.isVisible=true
                binding!!.llMain.isVisible=false
            }

        }
        viewModel.updatescanTicketLiveData.observe(this){
            showSuccessMessage(it.updateBookingDetails.toString())
            findNavController().popBackStack()
        }
    }

    override fun initComponents() {
        val bookingTicket = args.bookingScanTicket
        viewModel.callScannerTicketApi(bookingTicket.toString())
        ( requireActivity() as MainActivity).hideBottom()
        initRecyclerView()
    }

    private fun setData() {
        binding?.apply {
            if (ticketBookingDetailsReq.isNotEmpty()) {
                tvEventName.text = ticketBookingDetailsReq[0].event_title
                Log.e("", "setData====: ${tvEventName.text}", )
                tvEventDate.text = ticketBookingDetailsReq[0].event_date
                ivEvent.loadImage(requireActivity() , BuildConfig.BASE_URL+ticketBookingDetailsReq[0].image, catchImage = true)
                tvCustomerName.text = ticketBookingDetailsReq[0].customer_name
                tvCustomerMobileNumber.text = ticketBookingDetailsReq[0].customer_mobile
                tvCheckIn.text = ticketBookingDetailsReq[0].start_time
            }
        }
    }

    override fun setClickListener() {
        binding!!.btnLogin.clickWithDebounce {

           val  ticketNumbers = ticketBookingDetailsReq
                .filter { it.selected } // ✅ Only selected items
                .map { it.booking_qr_number.toString() } // ✅ Extract booking_qr_number
                .joinToString(",")
            viewModel.callUpdateScanTicketApi(ticketNumbers)
        }
        binding!!.ivBack.clickWithDebounce {
            findNavController().popBackStack()
        }

    }
    private fun initRecyclerView() {
        ticketScanBookingAapter = TicketScanBookingAapter(requireContext(), ticketBookingDetailsReq) { view, model, position ->
            when (view.id) {
                R.id.clMainBooking ->{

                }
            }
        }
        binding!!.rvCoupons.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        binding!!.rvCoupons.adapter = ticketScanBookingAapter

    }
}