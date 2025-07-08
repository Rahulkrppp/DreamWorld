package com.app.dreamworld.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.dreamworld.ui.auth.AuthenticationRepository
import com.app.dreamworld.ui.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class ProfileViewModel  @Inject constructor(private val authenticationRepo: AuthenticationRepository) : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}