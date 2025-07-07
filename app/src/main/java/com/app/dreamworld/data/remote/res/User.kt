package com.app.dreamworld.data.remote.res

import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("id") var userId: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("mobile") var mobile: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("role_id") var roleId: Int? = null,
    @SerializedName("role_name") var roleName: String? = null,
    @SerializedName("branch_id") var branchId: String? = null,
    @SerializedName("branch_name") var branchName: String? = null,
)
