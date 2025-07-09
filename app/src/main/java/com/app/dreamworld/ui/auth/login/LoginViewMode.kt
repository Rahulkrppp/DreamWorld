package com.app.dreamworld.ui.auth.login

import android.os.Build
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.app.dreamworld.data.remote.ApiService
import com.app.dreamworld.data.remote.cit.WSObjectResponse
import com.app.dreamworld.data.remote.di.DataClass
import com.app.dreamworld.data.remote.di.helper.AuthenticationRepoHelper
import com.app.dreamworld.data.remote.req.LoginRequest
import com.app.dreamworld.data.remote.res.User
import com.app.dreamworld.ui.core.BaseApplication
import com.app.dreamworld.ui.core.BaseViewModel
import com.app.dreamworld.util.preference.EasyPref
import com.google.gson.JsonElement
import dagger.hilt.android.lifecycle.HiltViewModel
import de.fast2work.mobility.utility.helper.SingleLiveData


import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  Login View model
 */
@HiltViewModel
class LoginViewModel @Inject constructor(private val authenticationRepo: AuthenticationRepoHelper) : BaseViewModel() {

    var loginLiveData = SingleLiveData<DataClass>()
    var changePasswordLiveData = SingleLiveData<DataClass>()
    var forgotPasswordLiveData = SingleLiveData<DataClass>()

    /**
     * This function contains code for login api call
     *
     * @param email
     * @param password
     */
    fun callLoginApi(email: String, password: String, deviceId:String) {
        viewModelScope.launch {
            val param = HashMap<String,String>()
            param["email"] = email
            param["password"] = password

            invalidateLoading(true)
            try {
            authenticationRepo.callLoginApi(param, onResult = {
                invalidateLoading(false)
                loginLiveData.postValue(it.data)
                //if (it.data?.twoFactorEnabled.toBlankString().equals("No", true)){
                //}
            }, onFailure = {
                invalidateLoading(false)
                errorLiveData.postValue(it)
            })} catch (e:Exception){
                print(e)
            }
        }
    }
    /**
     *
     */
    fun callChangePasswordApi(userId: String, password: String) {
        viewModelScope.launch {
            val param = HashMap<String,String>()
            param["userID"] = userId
            param["password"] = password

            invalidateLoading(true)
            try {
                authenticationRepo.callChangePasswordApi(param, onResult = {
                    invalidateLoading(false)
                    changePasswordLiveData.postValue(it.data)
                    //if (it.data?.twoFactorEnabled.toBlankString().equals("No", true)){
                    //}
                }, onFailure = {
                    invalidateLoading(false)
                    errorLiveData.postValue(it)
                })} catch (e:Exception){
                print(e)
            }
        }
    }

    /**
     *
     */
    fun callForgotPasswordApi(email: String) {
        viewModelScope.launch {
            val param = HashMap<String,String>()
            param["email"] = email

            invalidateLoading(true)
            try {
                authenticationRepo.callForgotPasswordApi(param, onResult = {
                    invalidateLoading(false)
                    forgotPasswordLiveData.postValue(it.data)

                }, onFailure = {
                    invalidateLoading(false)
                    errorLiveData.postValue(it)
                })} catch (e:Exception){
                print(e)
            }
        }
    }

}