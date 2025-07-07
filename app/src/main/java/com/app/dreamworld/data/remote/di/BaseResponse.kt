package com.app.dreamworld.data.remote.di

import com.app.dreamworld.data.remote.res.Event
import com.app.dreamworld.data.remote.res.User
import com.google.gson.annotations.SerializedName

class LoginResponse {

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

    @SerializedName("events")
    var events:ArrayList<Event> = arrayListOf()
}