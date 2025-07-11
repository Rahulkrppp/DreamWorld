package com.app.dreamworld.data.remote.cit

import com.google.gson.annotations.SerializedName
import de.fast2work.mobility.data.remote.cit.HBBaseResponse

class WSObjectResponse<T> : HBBaseResponse() {
    @SerializedName("data")
    var data: T? = null
}
