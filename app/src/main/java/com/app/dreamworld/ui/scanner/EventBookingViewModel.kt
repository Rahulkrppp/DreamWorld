package com.app.dreamworld.ui.scanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dreamworld.data.remote.di.DataClass
import com.app.dreamworld.ui.auth.AuthenticationRepository
import com.app.dreamworld.ui.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.fast2work.mobility.utility.helper.SingleLiveData
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class EventBookingViewModel  @Inject constructor(private val authenticationRepo: AuthenticationRepository) : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
    var eventLiveData = SingleLiveData<DataClass>()

    fun callEventCustomerApi() {
        viewModelScope.launch {
            invalidateLoading(true)
            try {
                authenticationRepo.callEventCustomerApi( onResult = {
                    invalidateLoading(false)
                    eventLiveData.postValue(it.data)

                }, onFailure = {
                    invalidateLoading(false)
                    errorLiveData.postValue(it)
                })} catch (e:Exception){
                print(e)
            }
        }
    }
}