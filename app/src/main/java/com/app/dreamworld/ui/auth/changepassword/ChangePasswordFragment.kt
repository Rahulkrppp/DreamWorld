package com.app.dreamworld.ui.auth.changepassword

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.dreamworld.MainActivity
import com.app.dreamworld.R
import com.app.dreamworld.data.remote.res.User
import com.app.dreamworld.databinding.FragmentChangePasswordBinding
import com.app.dreamworld.databinding.FragmentProfileBinding
import com.app.dreamworld.ui.auth.login.Login
import com.app.dreamworld.ui.auth.login.LoginViewModel
import com.app.dreamworld.ui.core.BaseApplication
import com.app.dreamworld.ui.core.BaseVMBindingFragment
import com.app.dreamworld.util.AsteriskPasswordTransformationMethod
import com.app.dreamworld.util.clickWithDebounce
import com.app.dreamworld.util.getTrimText
import com.app.dreamworld.util.isValidPassword
import com.app.dreamworld.util.preference.EasyPref


class ChangePasswordFragment :
    BaseVMBindingFragment<FragmentChangePasswordBinding, LoginViewModel>(LoginViewModel::class.java) {

        var isTogglePassShow=false
        var isToggleConfirmPassShow=false
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            return generateBinding(FragmentChangePasswordBinding.inflate(inflater), container)
        }

    override fun attachObservers() {
        viewModel.changePasswordLiveData.observe(this){
            BaseApplication.sharedPreference.clearAll()
            val intent = Intent(requireContext(), Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun initComponents() {
        binding!!.edtConfirmPassword.transformationMethod = AsteriskPasswordTransformationMethod()
        binding!!.edtPassword.transformationMethod = AsteriskPasswordTransformationMethod()
        (requireActivity() as MainActivity).hideBottom()
    }

    override fun setClickListener() {
        binding?.apply {
            imgConfirmPasswordToggle.clickWithDebounce {
                handleConfirmPasswordToggleImage()
            }
            imgPasswordToggle.clickWithDebounce {
                handlePasswordToggleImage()
            }
            btnChange.clickWithDebounce {
                if (isValid()){
                    viewModel.callChangePasswordApi(BaseApplication.sharedPreference.getPrefModel(EasyPref.USER_DATA,User::class.java)?.userId.toString(),binding!!.edtPassword.getTrimText())
                }
            }
        }

    }
    /**
     * This method contains code for all the password show or hide display in our app
     *
     */
    private fun handlePasswordToggleImage() {
        if (isTogglePassShow) {
            isTogglePassShow = false
            binding!!.imgPasswordToggle.setImageResource(R.drawable.ic_eye_on)
            binding!!.edtPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            isTogglePassShow = true
            binding!!.imgPasswordToggle.setImageResource(R.drawable.ic_eye_off)
            binding!!.edtPassword.transformationMethod =
                AsteriskPasswordTransformationMethod() //PasswordTransformationMethod.getInstance()
        }
        binding!!.edtPassword.setSelection(binding!!.edtPassword.length())
    }

    /**
     * This method contains code for all the password show or hide display in our app
     *
     */
    private fun handleConfirmPasswordToggleImage() {
        if (isToggleConfirmPassShow) {
            isToggleConfirmPassShow = false
            binding!!.imgConfirmPasswordToggle.setImageResource(R.drawable.ic_eye_on)
            binding!!.edtConfirmPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            isToggleConfirmPassShow = true
            binding!!.imgConfirmPasswordToggle.setImageResource(R.drawable.ic_eye_off)
            binding!!.edtConfirmPassword.transformationMethod =
                AsteriskPasswordTransformationMethod() //PasswordTransformationMethod.getInstance()
        }
        binding!!.edtConfirmPassword.setSelection(binding!!.edtConfirmPassword.length())
    }
    private fun isValid(): Boolean {

        if (!binding!!.edtPassword.getTrimText().isValidPassword()){
            showErrorMessage("Please enter the password in a valid format")
            return false
        }
        if (binding!!.edtPassword.getTrimText().isEmpty()){
            showErrorMessage("Please enter the new password")
            return false
        }
        if (binding!!.edtConfirmPassword.getTrimText() != binding!!.edtPassword.getTrimText()){
            showErrorMessage("Please enter the password the same as the confirm password field")
            return false
        }
        if (binding!!.edtConfirmPassword.getTrimText().isEmpty()){
            showErrorMessage("Please enter the confirm password")
            return false
        }
        return true
    }

}