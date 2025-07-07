package com.app.dreamworld.data.remote

import com.app.dreamworld.data.remote.cit.WSObjectResponse
import com.app.dreamworld.data.remote.di.LoginResponse
import com.app.dreamworld.data.remote.req.LoginRequest
import com.app.dreamworld.data.remote.res.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {


//
    @FormUrlEncoded
    @POST("index.php?action=login")
    suspend fun callLoginApi(@FieldMap params: HashMap<String, String>): Response<LoginResponse>


    @POST("index.php?action=all_event")
    suspend fun callEventApi(): Response<LoginResponse>
//
//    @POST("userApi/auth/verifyTwoFactorAuthCode")
//    suspend fun call2FALoginApi(@Body params: Login2FAReq): Response<WSObjectResponse<User>>


}