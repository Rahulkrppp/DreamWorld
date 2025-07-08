package com.app.dreamworld.ui.auth

import com.app.dreamworld.data.remote.ApiService
import com.app.dreamworld.data.remote.cit.HBSuccessCallback
import com.app.dreamworld.data.remote.di.BaseResponse
import com.app.dreamworld.data.remote.di.helper.AuthenticationRepoHelper
import com.app.dreamworld.ui.core.BaseRepository
import javax.inject.Inject

/**
 * This class use for all api call onSuccess or onFailure Authentication Repository
 */
class AuthenticationRepository @Inject constructor(private val apiService: ApiService) :
    BaseRepository(),
    AuthenticationRepoHelper {

    /**
     *  Login api call
     */
    override suspend fun callLoginApi(
        params: HashMap<String, String>,
        onResult: (response: BaseResponse) -> Unit,
        onFailure: (message: String) -> Unit,
    ) {
        try {
            safeApiCall(apiService.callLoginApi(params), object :
                HBSuccessCallback<BaseResponse> {
                override fun onSuccess(response: BaseResponse) {
                    if (response.success==true) {
                        onResult(response)
                    } else {
                        onFailure(response.msg.toString())
                    }
                }

                override fun onFailure(code: Int?, message: String?) {
                    onFailure(message!!)
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            onFailure(e.message.toString())
        }
    }


    override suspend fun callEventApi(
        onResult: (response: BaseResponse) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        try {
            safeApiCall(apiService.callEventApi(), object :
                HBSuccessCallback<BaseResponse> {
                override fun onSuccess(response: BaseResponse) {
                    if (response.success==true) {
                        onResult(response)
                    } else {
                        onFailure(response.msg.toString())
                    }
                }

                override fun onFailure(code: Int?, message: String?) {
                    onFailure(message!!)
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            onFailure(e.message.toString())
        }
    }

    override suspend fun callEventCustomerApi(
        onResult: (response: BaseResponse) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        try {
            safeApiCall(apiService.callEventCustomerApi(), object :
                HBSuccessCallback<BaseResponse> {
                override fun onSuccess(response: BaseResponse) {
                    if (response.success==true) {
                        onResult(response)
                    } else {
                        onFailure(response.msg.toString())
                    }
                }

                override fun onFailure(code: Int?, message: String?) {
                    onFailure(message!!)
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            onFailure(e.message.toString())
        }
    }

    override suspend fun callCustomerByEventApi(
        params: HashMap<String, String>,
        onResult: (response: BaseResponse) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        try {
            safeApiCall(apiService.callCustomerByEventApi(params), object :
                HBSuccessCallback<BaseResponse> {
                override fun onSuccess(response: BaseResponse) {
                    if (response.success==true) {
                        onResult(response)
                    } else {
                        onFailure(response.msg.toString())
                    }
                }

                override fun onFailure(code: Int?, message: String?) {
                    onFailure(message!!)
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            onFailure(e.message.toString())
        }
    }

    override suspend fun callShowBaseEventApi(
        params: HashMap<String, String>,
        onResult: (response: BaseResponse) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        try {
            safeApiCall(apiService.callShowBaseEventApi(params), object :
                HBSuccessCallback<BaseResponse> {
                override fun onSuccess(response: BaseResponse) {
                    if (response.success==true) {
                        onResult(response)
                    } else {
                        onFailure(response.msg.toString())
                    }
                }

                override fun onFailure(code: Int?, message: String?) {
                    onFailure(message!!)
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            onFailure(e.message.toString())
        }
    }

    override suspend fun callBookingTicketApi(
        params: HashMap<String, String>,
        onResult: (response: BaseResponse) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        try {
            safeApiCall(apiService.callBookingTicketApi(params), object :
                HBSuccessCallback<BaseResponse> {
                override fun onSuccess(response: BaseResponse) {
                    if (response.success==true) {
                        onResult(response)
                    } else {
                        onFailure(response.msg.toString())
                    }
                }

                override fun onFailure(code: Int?, message: String?) {
                    onFailure(message!!)
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            onFailure(e.message.toString())
        }
    }

    override suspend fun callScannerTicketApi(
        params: HashMap<String, String>,
        onResult: (response: BaseResponse) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        try {
            safeApiCall(apiService.callScannerTicketApi(params), object :
                HBSuccessCallback<BaseResponse> {
                override fun onSuccess(response: BaseResponse) {
                    if (response.success==true) {
                        onResult(response)
                    } else {
                        onFailure(response.msg.toString())
                    }
                }

                override fun onFailure(code: Int?, message: String?) {
                    onFailure(message!!)
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            onFailure(e.message.toString())
        }
    }

    override suspend fun callUpdateScanTicketApi(
        params: HashMap<String, String>,
        onResult: (response: BaseResponse) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        try {
            safeApiCall(apiService.callUpdateScanTicketApi(params), object :
                HBSuccessCallback<BaseResponse> {
                override fun onSuccess(response: BaseResponse) {
                    if (response.success==true) {
                        onResult(response)
                    } else {
                        onFailure(response.msg.toString())
                    }
                }

                override fun onFailure(code: Int?, message: String?) {
                    onFailure(message!!)
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            onFailure(e.message.toString())
        }
    }

}