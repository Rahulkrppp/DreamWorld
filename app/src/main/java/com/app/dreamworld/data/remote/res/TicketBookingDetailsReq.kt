package com.app.dreamworld.data.remote.res

import com.google.gson.annotations.SerializedName

data class TicketBookingDetailsReq(
    @SerializedName("image") var image: String? = null,
    @SerializedName("scanned_user") var scannedUser: String? = null,
    @SerializedName("is_scanned") var isScanned: String? = null,
    @SerializedName("scanned_at") var scannedAt: String? = null,
    @SerializedName("event_title") var event_title: String? = null,
    @SerializedName("event_date") var event_date: String? = null,
    @SerializedName("start_time") var start_time: String? = null,
    @SerializedName("customer_name") var customer_name: String? = null,
    @SerializedName("customer_mobile") var customer_mobile: String? = null,
    @SerializedName("seat_number") var seat_number: String? = null,
    @SerializedName("show_title") var show_title: String? = null,
    @SerializedName("booking_qr_number") var booking_qr_number: String? = null,

    var selected:Boolean=false
)
