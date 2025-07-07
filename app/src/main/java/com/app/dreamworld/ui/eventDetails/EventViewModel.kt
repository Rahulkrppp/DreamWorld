package com.app.dreamworld.ui.eventDetails

import com.app.dreamworld.ui.auth.AuthenticationRepository
import com.app.dreamworld.ui.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class EventViewModel  @Inject constructor(private val authenticationRepo: AuthenticationRepository) : BaseViewModel() {
}