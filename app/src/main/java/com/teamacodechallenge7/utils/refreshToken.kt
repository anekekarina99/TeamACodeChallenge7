package com.teamacodechallenge7.utils

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.teamacodechallenge7.data.local.SharedPref
import com.teamacodechallenge7.data.model.LoginRequest
import com.teamacodechallenge7.data.remote.ApiModule.service
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

private lateinit var disposable: Disposable
fun refreshToken() {
    val tag = "Handling"
    val mainHandler = Handler(Looper.getMainLooper())
    mainHandler.post(object : Runnable {
        override fun run() {
            val username = SharedPref.username.toString()
            val password = SharedPref.password.toString()
            val ms=30*1000*60
            val requestLogin = LoginRequest(username, password)
            disposable = service.loginAction(requestLogin)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    SharedPref.token = ("Bearer"+" "+it.data.token)
                    Log.e(tag, it.data.token)
                }) {
                    it.getServiceErrorMsg()
                    it.printStackTrace()
                }
            mainHandler.postDelayed(this, ms.toLong())
        }
    })
}