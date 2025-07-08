package com.app.dreamworld.ui.member

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
class MemberListViewModel  @Inject constructor(private val authenticationRepo: AuthenticationRepository) : BaseViewModel() {

    var eventLiveData = SingleLiveData<DataClass>()

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    fun callEventCustomerApi(eventId:String) {
        val param = HashMap<String,String>()
        param["eventID"] =eventId


        viewModelScope.launch {
            invalidateLoading(true)
            try {
                authenticationRepo.callCustomerByEventApi( param,onResult = {
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