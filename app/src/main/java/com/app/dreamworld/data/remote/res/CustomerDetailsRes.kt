package com.app.dreamworld.data.remote.res

import com.google.gson.annotations.SerializedName

data class CustomerDetailsRes (
    @SerializedName("customer_name") var customerName: String? = null,
    @SerializedName("customer_mobile") var customerMobile: String? = null,
    @SerializedName("availabe_seats") var availabeSeats: String? = null,
    @SerializedName("booking_ticket_number") var bookingTicketNumber: String? = null,
    @SerializedName("show_name") var showName: String? = null,

    )

