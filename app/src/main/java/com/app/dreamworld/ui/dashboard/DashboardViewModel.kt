package com.app.dreamworld.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.dreamworld.data.remote.di.DataClass
import com.app.dreamworld.data.remote.di.helper.AuthenticationRepoHelper
import com.app.dreamworld.ui.auth.AuthenticationRepository
import com.app.dreamworld.ui.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.fast2work.mobility.utility.helper.SingleLiveData
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val authenticationRepo: AuthenticationRepository) : BaseViewModel() {

    var eventLiveData = SingleLiveData<DataClass>()

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    fun callEventApi() {
        viewModelScope.launch {
            invalidateLoading(true)
            try {
                authenticationRepo.callEventApi( onResult = {
                    invalidateLoading(false)
                    eventLiveData.postValue(it.data)
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