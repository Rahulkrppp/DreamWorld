package com.app.dreamworld.ui.auth.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.app.dreamworld.MainActivity
import com.app.dreamworld.R
import com.app.dreamworld.data.remote.res.User
import com.app.dreamworld.databinding.ActivityLoginBinding
import com.app.dreamworld.ui.auth.ForgotPassword
import com.app.dreamworld.ui.core.BaseApplication
import com.app.dreamworld.ui.core.BaseVMBindingActivity
import com.app.dreamworld.util.AsteriskPasswordTransformationMethod
import com.app.dreamworld.util.clickWithDebounce
import com.app.dreamworld.util.extension.getAndroidDeviceId
import com.app.dreamworld.util.preference.EasyPref


class Login :
    BaseVMBindingActivity<ActivityLoginBinding, LoginViewModel>(LoginViewModel::class.java) {

        private var isTogglePassShow:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBindingView(ActivityLoginBinding.inflate(layoutInflater))

    }

    override fun attachObservers() {
        viewModel.loginLiveData.observe(this) {
            BaseApplication.sharedPreference.setPref(EasyPref.USER_DATA, it.data!!)
            Log.e("", "callLoginApi==============: ${BaseApplication.sharedPreference.getPrefModel(EasyPref.USER_DATA, User::class.java)} ", )
                Log.e("", "attachObservers:${it.data} ", )
                startActivity(Intent(this@Login,MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))
            finish()

        }
    }


    override fun initComponents() {
        binding.edtPassword.transformationMethod = AsteriskPasswordTransformationMethod()
    }

    override fun setClickListener() {
        binding.btnLogin.clickWithDebounce {
            viewModel.callLoginApi(binding.edtEmail.text.toString(),binding.edtPassword.text.toString(),getAndroidDeviceId())
           //
        }

        binding.tvForgot.clickWithDebounce {
            val bottomSheet = ForgotPassword()
            bottomSheet.show(supportFragmentManager,"ForgotPassword")
        }
        binding.imgPasswordToggle.clickWithDebounce {
            handleToggleImage()
        }
    }

    /**
     * This method contains code for all the password show or hide display in our app
     *
     */
    private fun handleToggleImage() {
        if (isTogglePassShow) {
            isTogglePassShow = false
            binding.imgPasswordToggle.setImageResource(R.drawable.ic_eye_on)
            binding.edtPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            isTogglePassShow = true
            binding.imgPasswordToggle.setImageResource(R.drawable.ic_eye_off)
            binding.edtPassword.transformationMethod = AsteriskPasswordTransformationMethod() //PasswordTransformationMethod.getInstance()
        }
        binding.edtPassword.setSelection(binding.edtPassword.length())
    }
}