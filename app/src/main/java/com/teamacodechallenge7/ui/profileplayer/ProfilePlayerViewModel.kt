package com.teamacodechallenge7.ui.profileplayer

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teamacodechallenge7.data.local.SharedPref
import com.teamacodechallenge7.data.model.LoginRequest
import com.teamacodechallenge7.data.model.Users
import com.teamacodechallenge7.data.remote.ApiModule.service
import com.teamacodechallenge7.utils.errorHandling
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ProfilePlayerViewModel(
    private val pref: SharedPref
) : ViewModel() {

    private var tag = "ProfilePlayer"
    private lateinit var disposable: Disposable
    var resultUser = MutableLiveData<Users>()
    var resultMessage = MutableLiveData<String>()
    
    fun playerData() {
        var token =
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MDE1N2I0OGRiNzg0ODAwMTczZjk4YjkiLCJ1c2VybmFtZSI6ImFndW5ndyIsImVtYWlsIjoiYWd1bmd3QHlhaG9vLmNvbSIsImlhdCI6MTYxMjM1NzQxMCwiZXhwIjoxNjEyMzY0NjEwfQ.Knn2frT7Wnvldl6iNmSEI5ec3yJlO5N1h2cU-MvDdC8"

        disposable = service.getUsers(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    SharedPref.email = it.data.email
                    SharedPref.username = it.data.username
                    SharedPref.url_profile = it.data.photo
                    resultUser.value = it
                },
                {
                    val msg: String = errorHandling(it)
                    Log.e(tag, msg)
                    if (msg.equals("Token is expired")|| msg.equals("Invalid Token") ) {
                        resultMessage.value = msg
                    }
                    it.printStackTrace()
                })

    }

    class Factory(
        private val pref: SharedPref
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProfilePlayerViewModel(pref) as T
        }
    }
}