package com.app.dreamworld.data.remote.di

import com.app.dreamworld.data.remote.res.CustomerDetailsRes
import com.app.dreamworld.data.remote.res.Event
import com.app.dreamworld.data.remote.res.EventBookingList
import com.app.dreamworld.data.remote.res.ShowsDetailsRes
import com.app.dreamworld.data.remote.res.TicketBookingDetailsReq
import com.app.dreamworld.data.remote.res.User
import com.google.gson.annotations.SerializedName

class BaseResponse {

    @SerializedName("data")
    var data:DataClass? = null

    @SerializedName("success")
    var success:Boolean? = false

    @SerializedName("msg")
    var msg:String? = null
}

class DataClass {

    @SerializedName("user")
    var data:User? = null

    @SerializedName("booking_details")
    var bookingDetails:String?=null

    @SerializedName("events")
    var events:ArrayList<Event> = arrayListOf()

    @SerializedName("event_booking_details")
    var eventBookingDetails:ArrayList<EventBookingList> = arrayListOf()

    @SerializedName("customer_details")
    var customerDetails:ArrayList<CustomerDetailsRes> = arrayListOf()

    @SerializedName("shows_details")
    var showsDetails:ArrayList<ShowsDetailsRes> = arrayListOf()

    @SerializedName("ticket_booking_details")
    var ticketBookingDetails:ArrayList<TicketBookingDetailsReq> = arrayListOf()

    @SerializedName("update_booking_details")
    var updateBookingDetails:String? = null

    @SerializedName("forgot_password")
    var forgotPassword:String? = null
}