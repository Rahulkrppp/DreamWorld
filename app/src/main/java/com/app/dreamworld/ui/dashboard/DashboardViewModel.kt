package com.app.dreamworld.ui.dashboard

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.dreamworld.data.remote.di.DataClass
import com.app.dreamworld.data.remote.di.helper.AuthenticationRepoHelper
import com.app.dreamworld.ui.auth.AuthenticationRepository
import com.app.dreamworld.ui.core.BaseViewModel
import com.app.dreamworld.util.extension.isNetworkAvailable
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import de.fast2work.mobility.utility.helper.SingleLiveData
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val authenticationRepo: AuthenticationRepository) : BaseViewModel() {

    var eventLiveData = SingleLiveData<DataClass>()
    var showBaseEventLiveData = SingleLiveData<DataClass>()
    var showBookingTicketEventLiveData = SingleLiveData<DataClass>()

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    fun callEventApi(context: Context) {
        if (context.applicationContext.isNetworkAvailable()) {
            viewModelScope.launch {
                invalidateLoading(true)
                try {
                    authenticationRepo.callEventApi(onResult = {
                        invalidateLoading(false)
                        eventLiveData.postValue(it.data)
                        //if (it.data?.twoFactorEnabled.toBlankString().equals("No", true)){
                        //}
                    }, onFailure = {
                        invalidateLoading(false)
                        errorLiveData.postValue(it)
                    })
                } catch (e: Exception) {
                    print(e)
                }
            }
        }
    }

    fun callShowBaseEventApi(eventId:String) {
        viewModelScope.launch {
            val param = HashMap<String,String>()
            param["eventID"] =eventId
            invalidateLoading(true)
            try {
                authenticationRepo.callShowBaseEventApi(param, onResult = {
                    invalidateLoading(false)
                    showBaseEventLiveData.postValue(it.data)
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

    fun callBookingTicketApi(name:String,mobile:String,show:String,numberOfPass:String,amount:String,event:String,staffId:String) {
        viewModelScope.launch {
            val param = HashMap<String,String>()
            param["name"] =name
            param["mobile"] =mobile
            param["show"] =show
            param["number_of_pass"] =numberOfPass
            param["amount"] =amount
            param["event"] =event
            param["staff_id"] =staffId
            invalidateLoading(true)
            try {
                authenticationRepo.callBookingTicketApi(param, onResult = {
                    invalidateLoading(false)
                    showBookingTicketEventLiveData.postValue(it.data)
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

}