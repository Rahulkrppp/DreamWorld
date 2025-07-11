package com.app.dreamworld.data.remote.req

import com.google.gson.annotations.SerializedName

data class LoginRequest (
    @SerializedName("email") var email: String = "",
    @SerializedName("password") var password: String = "",
    @SerializedName("deviceType") val deviceType : String = "android",
    @SerializedName("deviceId") var deviceId: String = ""
)