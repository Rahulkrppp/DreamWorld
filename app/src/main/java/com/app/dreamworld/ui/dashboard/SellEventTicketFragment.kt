package com.app.dreamworld.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.app.dreamworld.MainActivity
import com.app.dreamworld.R
import com.app.dreamworld.data.remote.res.Event
import com.app.dreamworld.data.remote.res.ShowsDetailsRes
import com.app.dreamworld.data.remote.res.User
import com.app.dreamworld.databinding.FragmentCustomerBinding
import com.app.dreamworld.databinding.FragmentDashboardBinding
import com.app.dreamworld.ui.core.BaseApplication
import com.app.dreamworld.ui.core.BaseVMBindingFragment
import com.app.dreamworld.ui.dashboard.bottom.SelectShowBottomSheetFragment
import com.app.dreamworld.ui.eventDetails.EventDetailFragmentArgs
import com.app.dreamworld.ui.eventDetails.bottom.SearchBottomSheetFragment
import com.app.dreamworld.util.clickWithDebounce
import com.app.dreamworld.util.getTrimText
import com.app.dreamworld.util.getTrimmedText
import com.app.dreamworld.util.isValidEmail
import com.app.dreamworld.util.preference.EasyPref

class SellEventTicketFragment : BaseVMBindingFragment<FragmentCustomerBinding, DashboardViewModel>(DashboardViewModel::class.java) {


    val args: SellEventTicketFragmentArgs by navArgs()
     var showsDetailsList:ArrayList<ShowsDetailsRes> = arrayListOf()
    var showId=""
    var event: Event?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return generateBinding(FragmentCustomerBinding.inflate(inflater), container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickListeners()
         event = args.event
        ( requireActivity() as MainActivity).hideBottom()
        viewModel.callShowBaseEventApi(event?.id.toString())
        binding!!.tvName.text=event?.title
    }

    override fun attachObservers() {
        viewModel.showBaseEventLiveData.observe(this){
            showsDetailsList.clear()
            showsDetailsList.addAll(it.showsDetails)
        }
        viewModel.showBookingTicketEventLiveData.observe(this){
            //findNavController().popBackStack()
            binding!!.edtName.setText("")
            binding!!.edtPhone.setText("")
            binding!!.edtMemberPass.setText("")
            binding!!.edtPrice.setText("")
            binding!!.edtShow.text = getString(R.string.select_show)
            showId=""
            showSuccessMessage(it.bookingDetails.toString())
        }

    }
    override fun initComponents() {

    }

    override fun setClickListener() {
        binding?.apply {
            edtShow.clickWithDebounce {
                val dialog = SelectShowBottomSheetFragment.newInstance(showsDetailsList)
                dialog.sendClickListener = {
                    showId=it.showId.toString()
                    binding!!.edtShow.text = it.title
                    dialog.dismiss()
                }
                dialog.show(childFragmentManager, "")
            }
            binding!!.edtMemberPass.doOnTextChanged { text, start, before, count ->
                val input = text.toString()
                if (input.all { it.isDigit() }) {
                    val number = input.toIntOrNull()
                    if (number != null) {
                        if (number > 3) {
                            val result = (number-3) * event?.amount?.toInt()!!
                            binding!!.edtPrice.setText(result.toString())
                        }else{
                            binding!!.edtPrice.setText("0")
                        }
                    }else{
                        binding!!.edtPrice.setText("0")
                    }
                }else{
                    binding!!.edtPrice.setText("0")
                }

            }
            btnPurchase.clickWithDebounce {
                if (isValid()) {
                    viewModel.callBookingTicketApi(
                        binding!!.edtName.getTrimText(),
                        binding!!.edtPhone.getTrimText(),
                        showId,
                        binding!!.edtMemberPass.getTrimmedText(),
                        binding!!.edtPrice.getTrimmedText(),
                        event?.id.toString(),
                        BaseApplication.sharedPreference.getPrefModel(
                            EasyPref.USER_DATA,
                            User::class.java
                        )?.userId.toString()
                    )
                }
            }
        }

    }

    private fun onClickListeners(){
        binding?.iimgBack?.clickWithDebounce {
            findNavController().popBackStack()
        }
    }

    private fun isValid(): Boolean {

        if (binding!!.edtName.getTrimText().isEmpty()) {
            showErrorMessage("Please enter the name")
            return false
        }
        if (binding!!.edtPhone.getTrimText().isEmpty()) {
            showErrorMessage("Please enter the mobile number")
            return false
        }
        if (binding!!.edtPhone.getTrimText().length < 10) {
            showErrorMessage("Please enter the valid mobile number")
            return false
        }
        if (binding!!.edtShow.text==getString(R.string.select_show)) {
            showErrorMessage("Please select the show")
            return false
        }
        if (binding!!.edtMemberPass.getTrimmedText().isEmpty()) {
            showErrorMessage("Please enter the number of pass")
            return false
        }
        if (binding!!.edtPrice.getTrimmedText().isEmpty()) {
            showErrorMessage("Please enter the amount")
            return false
        }
        return true
    }



    override fun onDestroyView() {
        super.onDestroyView()

    }
}