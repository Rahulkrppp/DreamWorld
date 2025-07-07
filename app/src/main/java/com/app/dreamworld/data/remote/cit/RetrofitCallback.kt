package com.app.dreamworld.data.remote.cit

interface RetrofitCallback<T> {
    fun onSuccess(response: T)
    fun onFailure(code: Int?, message: String?)
}