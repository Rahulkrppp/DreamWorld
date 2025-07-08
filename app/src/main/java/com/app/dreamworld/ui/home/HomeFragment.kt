package com.app.dreamworld.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dreamworld.MainActivity
import com.app.dreamworld.R
import com.app.dreamworld.data.remote.res.Event
import com.app.dreamworld.data.remote.res.User
import com.app.dreamworld.databinding.FragmentDashboardBinding
import com.app.dreamworld.databinding.FragmentHomeBinding
import com.app.dreamworld.ui.core.BaseApplication
import com.app.dreamworld.ui.core.BaseVMBindingFragment
import com.app.dreamworld.ui.dashboard.DashboardViewModel
import com.app.dreamworld.ui.dashboard.adapter.EventAdapter
import com.app.dreamworld.ui.eventDetails.EventDetailFragment
import com.app.dreamworld.util.clickWithDebounce
import com.app.dreamworld.util.preference.EasyPref

class HomeFragment :
    BaseVMBindingFragment<FragmentHomeBinding, HomeViewModel>(HomeViewModel::class.java) {


    // This property is only valid between onCreateView and
    // onDestroyView.
    private  var eventAdapter:EventAdapter? = null
    private var eventList:ArrayList<Event> = arrayListOf()
    private var upcomingEvent=0
    private var completeEvent=0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return generateBinding(FragmentHomeBinding.inflate(inflater), container)
    }

    override fun attachObservers() {
        viewModel.eventLiveData.observe(this){
            eventList.clear()
            eventList.addAll(it.events)
            eventList.forEachIndexed { index, event ->
                if (event.eventStatus=="Upcoming"){
                    upcomingEvent++
                }else{
                    completeEvent++
                }
            }
            if (eventList.isEmpty()){
                binding!!.tvNoData.isVisible=true
                binding!!.rvHomeEvent.isVisible=false
            }else{
                binding!!.tvNoData.isVisible=false
                binding!!.rvHomeEvent.isVisible=true
            }
            eventAdapter?.notifyDataSetChanged()
            binding!!.tvEventCount.text=upcomingEvent.toString()
            binding!!.tvCompletedEventCount.text=completeEvent.toString()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.imgLogo?.clickWithDebounce {
            findNavController().navigate(R.id.openSellTicketScreenFromHome)
        }

    }

    override fun initComponents() {
        if (isFirstTimeLoad){
            initRecyclerView()
            viewModel.callEventApi()
            setData()
        }
    }

    private fun setData() {
        binding?.apply {
            val user=BaseApplication.sharedPreference.getPrefModel(EasyPref.USER_DATA, User::class.java)
            tvCurrentUser.text=user?.name
            tvBranch.text=user?.branchName

        }

    }

    override fun setClickListener() {

    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as MainActivity).showBottom()
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
    private fun initRecyclerView() {
        eventAdapter = EventAdapter(requireContext(), eventList) { view, model, position ->
            when (view.id) {
                R.id .cl_main ->{
                    val action = HomeFragmentDirections.openEventDetails(model)
                    findNavController().navigate(action)
                }

            }
        }
        binding!!.rvHomeEvent.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        binding!!.rvHomeEvent.adapter = eventAdapter

    }
}