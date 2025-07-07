package com.app.dreamworld.data.remote.cit

interface HBSuccessCallback<T> : RetrofitCallback<T> {
    override fun onSuccess(response: T)
    override fun onFailure(code: Int?, message: String?)
}