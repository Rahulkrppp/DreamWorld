package com.app.dreamworld.data.remote.di.helper

import com.app.dreamworld.data.remote.di.BaseResponse


interface AuthenticationRepoHelper {
    suspend fun callLoginApi(
        params: HashMap<String, String>,
        onResult: (response: BaseResponse) -> Unit,
        onFailure: (message: String) -> Unit
    )
    suspend fun callEventApi(onResult: (response: BaseResponse) -> Unit, onFailure: (message: String) -> Unit)

    suspend fun callEventCustomerApi(onResult: (response: BaseResponse) -> Unit, onFailure: (message: String) -> Unit)

    suspend fun callCustomerByEventApi( params: HashMap<String, String>,onResult: (response: BaseResponse) -> Unit, onFailure: (message: String) -> Unit)

    suspend fun callShowBaseEventApi( params: HashMap<String, String>,onResult: (response: BaseResponse) -> Unit, onFailure: (message: String) -> Unit)
    suspend fun callBookingTicketApi( params: HashMap<String, String>,onResult: (response: BaseResponse) -> Unit, onFailure: (message: String) -> Unit)
    suspend fun callScannerTicketApi( params: HashMap<String, String>,onResult: (response: BaseResponse) -> Unit, onFailure: (message: String) -> Unit)
}

