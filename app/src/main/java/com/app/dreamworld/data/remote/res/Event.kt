package com.app.dreamworld.data.remote.res

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(
    @SerializedName("id") var id: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("event_date") var event_date: String? = null,
    @SerializedName("venue_id") var venue_id: String? = null,
    @SerializedName("uploaded_file") var uploaded_file: String? = null,
    @SerializedName("created_at") var created_at: String? = null,
    @SerializedName("venue_name") var venue_name: String? = null,
    @SerializedName("venue_address") var venue_address: String? = null,
    @SerializedName("person_capacity") var personCapacity: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("event_status") var eventStatus: String? = null,
    @SerializedName("amount") var amount: String? = null,
) : Parcelable

