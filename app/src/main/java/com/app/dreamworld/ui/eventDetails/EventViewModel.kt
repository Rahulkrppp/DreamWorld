package com.app.dreamworld.ui.eventDetails

import androidx.lifecycle.viewModelScope
import com.app.dreamworld.data.remote.di.DataClass
import com.app.dreamworld.ui.auth.AuthenticationRepository
import com.app.dreamworld.ui.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.fast2work.mobility.utility.helper.SingleLiveData
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class EventViewModel  @Inject constructor(private val authenticationRepo: AuthenticationRepository) : BaseViewModel() {
     var scanTicketLiveData = SingleLiveData<DataClass>()

    fun callScannerTicketApi(ticketID:String) {
        viewModelScope.launch {
            val param = HashMap<String,String>()
            param["ticketID"] =ticketID
            invalidateLoading(true)
            try {
                authenticationRepo.callScannerTicketApi(param, onResult = {
                    invalidateLoading(false)
                    scanTicketLiveData.postValue(it.data)
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