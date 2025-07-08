package com.app.dreamworld.data.remote.res

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShowsDetailsRes(
    @SerializedName("show_id") var showId: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("start_time") var startTime: String? = null,
    @SerializedName("end_time") var endTime: String? = null,
) : Parcelable
