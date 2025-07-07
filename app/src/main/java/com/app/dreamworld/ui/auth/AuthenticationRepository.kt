package com.app.dreamworld.ui.auth

import com.app.dreamworld.data.remote.ApiService
import com.app.dreamworld.data.remote.cit.HBSuccessCallback
import com.app.dreamworld.data.remote.cit.WSObjectResponse
import com.app.dreamworld.data.remote.di.LoginResponse
import com.app.dreamworld.data.remote.di.helper.AuthenticationRepoHelper
import com.app.dreamworld.data.remote.req.LoginRequest
import com.app.dreamworld.data.remote.res.User
import com.app.dreamworld.ui.core.BaseRepository
import de.fast2work.mobility.data.remote.cit.HBBaseResponse
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
        onResult: (response: LoginResponse) -> Unit,
        onFailure: (message: String) -> Unit,
    ) {
        try {
            safeApiCall(apiService.callLoginApi(params), object :
                HBSuccessCallback<LoginResponse> {
                override fun onSuccess(response: LoginResponse) {
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
        onResult: (response: LoginResponse) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        try {
            safeApiCall(apiService.callEventApi(), object :
                HBSuccessCallback<LoginResponse> {
                override fun onSuccess(response: LoginResponse) {
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