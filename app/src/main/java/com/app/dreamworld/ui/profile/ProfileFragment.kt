package com.app.dreamworld.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.dreamworld.data.remote.res.User
import com.app.dreamworld.databinding.FragmentHomeBinding
import com.app.dreamworld.databinding.FragmentProfileBinding
import com.app.dreamworld.ui.auth.login.Login
import com.app.dreamworld.ui.core.BaseApplication
import com.app.dreamworld.ui.core.BaseVMBindingFragment
import com.app.dreamworld.ui.dashboard.DashboardViewModel
import com.app.dreamworld.util.preference.EasyPref

class ProfileFragment :
    BaseVMBindingFragment<FragmentProfileBinding, DashboardViewModel>(DashboardViewModel::class.java) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return generateBinding(FragmentProfileBinding.inflate(inflater), container)
    }

    override fun attachObservers() {

    }

    override fun initComponents() {
        setData()
    }

    private fun setData() {
        binding?.apply {
            val user=BaseApplication.sharedPreference.getPrefModel(EasyPref.USER_DATA,User::class.java)
            UserName.text=user?.name
            Phone.text=user?.mobile
            Branch.text=user?.branchName
        }
    }

    override fun setClickListener() {
        binding?.btnLogout?.clickWithDebounce {
            BaseApplication.sharedPreference.clearAll()
            val intent = Intent(requireContext(), Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}