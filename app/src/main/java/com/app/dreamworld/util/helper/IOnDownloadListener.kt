package com.app.dreamworld.util.helper

interface IOnDownloadListener {
    fun onSuccess(filePath:String)
    fun onFailure()
    fun onInProgress()
}