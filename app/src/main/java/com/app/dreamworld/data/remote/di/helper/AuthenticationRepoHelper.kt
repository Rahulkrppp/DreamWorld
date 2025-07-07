package com.app.dreamworld.data.remote.di.helper

import com.app.dreamworld.data.remote.di.LoginResponse


interface AuthenticationRepoHelper {
    suspend fun callLoginApi(
        params: HashMap<String, String>,
        onResult: (response: LoginResponse) -> Unit,
        onFailure: (message: String) -> Unit
    )
    suspend fun callEventApi(onResult: (response: LoginResponse) -> Unit, onFailure: (message: String) -> Unit)
}

