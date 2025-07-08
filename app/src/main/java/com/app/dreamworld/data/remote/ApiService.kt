package com.app.dreamworld.data.remote

import com.app.dreamworld.data.remote.di.BaseResponse
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {


//
    @FormUrlEncoded
    @POST("index.php?action=login")
    suspend fun callLoginApi(@FieldMap params: HashMap<String, String>): Response<BaseResponse>


    @POST("index.php?action=all_event")
    suspend fun callEventApi(): Response<BaseResponse>

    @POST("index.php?action=getEventCustomerBooking")
    suspend fun callEventCustomerApi(): Response<BaseResponse>


    @GET("index.php?action=getCustomerBookingByEvent")
    suspend fun callCustomerByEventApi(@QueryMap params: HashMap<String, String>): Response<BaseResponse>

    @GET("index.php?action=getallshow_based_on_event")
    suspend fun callShowBaseEventApi(@QueryMap params: HashMap<String, String>): Response<BaseResponse>

    @GET("index.php?action=getScannerTicket")
    suspend fun callScannerTicketApi(@QueryMap params: HashMap<String, String>): Response<BaseResponse>

    @FormUrlEncoded
    @POST("index.php?action=bookTicket")
    suspend fun callBookingTicketApi(@FieldMap params: HashMap<String, String>): Response<BaseResponse>


//
//    @POST("userApi/auth/verifyTwoFactorAuthCode")
//    suspend fun call2FALoginApi(@Body params: Login2FAReq): Response<WSObjectResponse<User>>


}