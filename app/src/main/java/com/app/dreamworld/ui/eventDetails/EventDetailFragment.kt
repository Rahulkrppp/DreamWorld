package com.app.dreamworld.ui.eventDetails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.recreate
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.app.dreamworld.BuildConfig
import com.app.dreamworld.MainActivity
import com.app.dreamworld.R
import com.app.dreamworld.data.remote.res.Event
import com.app.dreamworld.databinding.FragmentEventDetailBinding
import com.app.dreamworld.databinding.FragmentHomeBinding
import com.app.dreamworld.ui.core.BaseVMBindingFragment
import com.app.dreamworld.ui.dashboard.DashboardViewModel
import com.app.dreamworld.ui.eventDetails.bottom.SearchBottomSheetFragment
import com.app.dreamworld.ui.home.HomeFragmentDirections
import com.app.dreamworld.util.clickWithDebounce
import com.app.dreamworld.util.extension.DISPLAY_FORMAT
import com.app.dreamworld.util.extension.SERVER_FORMAT
import com.app.dreamworld.util.extension.displayDate
import com.app.dreamworld.util.loadImage
import io.github.g00fy2.quickie.QRResult
import io.github.g00fy2.quickie.ScanQRCode
import kotlin.math.log


class EventDetailFragment :
    BaseVMBindingFragment<FragmentEventDetailBinding, EventViewModel>(EventViewModel::class.java) {
    val args: EventDetailFragmentArgs by navArgs()

    private val scanQrCode = registerForActivityResult(ScanQRCode()) {
        when (it) {
            is QRResult.QRSuccess -> {
                Log.e("", "scanResult===:${it.content.rawValue} ", )
                val action = EventDetailFragmentDirections.openBookingScanTicket(it.content.rawValue)
                findNavController().navigate(action)

            }
            QRResult.QRUserCanceled -> "userCanceled"
            QRResult.QRMissingPermission -> "missingPermission"
            is QRResult.QRError -> "${it.exception.javaClass.simpleName}: ${it.exception.localizedMessage}"
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return generateBinding(FragmentEventDetailBinding.inflate(inflater), container)
    }



    override fun attachObservers() {

    }

    override fun initComponents() {
        val event = args.event
        setData(event)
        ( requireActivity() as MainActivity).hideBottom()
    }

    private fun setData(event:Event?) {
        binding?.apply {
            eventImage.loadImage(requireActivity() , BuildConfig.BASE_URL+event?.image, catchImage = true)
            tvEventName.text=event?.title
            tvDate.text=event?.event_date?.displayDate(SERVER_FORMAT,DISPLAY_FORMAT)
            tvEventLocation.text=event?.venue_address
        }
    }

    override fun setClickListener() {
        binding?.apply {
            rvQrCode.clickWithDebounce {
                scanQrCode.launch(null)
            }
            ivBack.clickWithDebounce {
                findNavController().popBackStack()
            }
            rvSearch.clickWithDebounce {
                val dialog = SearchBottomSheetFragment.newInstance()
                dialog.sendClickListener = {
                    if (it.isEmpty()){
                        showErrorMessage("Please enter booking number")
                    }else {
                        val action = EventDetailFragmentDirections.openBookingScanTicket(it)
                        findNavController().navigate(action)
                        dialog.dismiss()
                    }
                }
                dialog.show(childFragmentManager, "")
            }
            btnAddCostumer.clickWithDebounce {
                val action = EventDetailFragmentDirections.openAddCustomer(args.event)
                findNavController().navigate(action)
            }
        }
    }
}