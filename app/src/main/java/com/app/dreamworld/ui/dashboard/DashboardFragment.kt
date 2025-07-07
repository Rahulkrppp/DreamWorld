package com.app.dreamworld.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dreamworld.R
import com.app.dreamworld.data.remote.res.Event
import com.app.dreamworld.databinding.FragmentDashboardBinding
import com.app.dreamworld.ui.core.BaseApplication
import com.app.dreamworld.ui.core.BaseVMBindingFragment
import com.app.dreamworld.ui.dashboard.adapter.EventAdapter
import com.app.dreamworld.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment :
    BaseVMBindingFragment<FragmentDashboardBinding, DashboardViewModel>(DashboardViewModel::class.java) {

        var eventAdapter:EventAdapter? = null
        var eventList:ArrayList<Event> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return generateBinding(FragmentDashboardBinding.inflate(inflater), container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun attachObservers() {
        viewModel.eventLiveData.observe(this){
            Log.e("", "attachObservers========: ${it.events}", )
            eventList.clear()
            eventList.addAll(it.events)
            eventAdapter?.notifyDataSetChanged()
        }
    }


    override fun initComponents() {
        viewModel.callEventApi()
        initRecyclerView()
        setData()
    }

    private fun setData() {
        binding?.apply {

        }
    }

    override fun setClickListener() {

    }


    override fun onDestroyView() {
        super.onDestroyView()

    }
    private fun initRecyclerView() {
        eventAdapter = EventAdapter(requireContext(), eventList) { view, model, position ->
            when (view.id) {
                R.id.cl_main ->{
                    val action = DashboardFragmentDirections.openEventDetailsList(model)
                    findNavController().navigate(action)
                }

            }
        }
        binding!!.rvEvent.layoutManager = LinearLayoutManager(requireActivity())

        binding!!.rvEvent.adapter = eventAdapter

    }
}