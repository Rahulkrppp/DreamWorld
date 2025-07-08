package com.app.dreamworld.data.remote.res

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventBookingList (
    @SerializedName("event_date") var eventDate: String? = null,
    @SerializedName("event_title") var eventTitle: String? = null,
    @SerializedName("total_seats") var totalSeats: String? = null,
    @SerializedName("booked_seats") var bookedSeats: String? = null,
    @SerializedName("available_seats") var availableSeats: String? = null,
    @SerializedName("event_id") var eventId: String? = null,

    ) : Parcelable