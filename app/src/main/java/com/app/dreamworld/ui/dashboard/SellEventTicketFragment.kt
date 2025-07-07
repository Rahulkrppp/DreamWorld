package com.app.dreamworld.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.app.dreamworld.MainActivity
import com.app.dreamworld.databinding.FragmentCustomerBinding
import com.app.dreamworld.databinding.FragmentDashboardBinding
import com.app.dreamworld.ui.core.BaseVMBindingFragment
import com.app.dreamworld.ui.eventDetails.EventDetailFragmentArgs
import com.app.dreamworld.util.clickWithDebounce

class SellEventTicketFragment : BaseVMBindingFragment<FragmentCustomerBinding, DashboardViewModel>(DashboardViewModel::class.java) {


    val args: SellEventTicketFragmentArgs by navArgs()




    // This property is only valid between onCreateView and
    // onDestroyView.

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
        val event = args.event
        ( requireActivity() as MainActivity).hideBottom()
    }

    override fun attachObservers() {

    }
    override fun initComponents() {

    }

    override fun setClickListener() {

    }

    private fun onClickListeners(){
        binding?.iimgBack?.clickWithDebounce {
            findNavController().popBackStack()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()

    }
}