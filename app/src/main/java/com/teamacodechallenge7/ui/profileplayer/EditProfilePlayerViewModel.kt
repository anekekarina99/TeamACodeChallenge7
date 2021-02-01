package com.teamacodechallenge7.ui.profileplayer

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teamacodechallenge7.data.local.SharedPref
import com.teamacodechallenge7.data.model.GetUsers
import com.teamacodechallenge7.data.remote.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class EditProfilePlayerViewModel(
    private val service: ApiService,
    private val pref: SharedPref
) : ViewModel() {

    private val tag : String = "EditProfilePlayer"
    private var disposable: Disposable? = null
    var resultUsers = MutableLiveData<GetUsers>()

    fun playerData() {
//        val token = pref.auth!!
        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MDE1N2I0OGRiNzg0ODAwMTczZjk4YjkiLCJ1c2VybmFtZSI6ImFndW5ndyIsImVtYWlsIjoiYWd1bmdAeWFob28uY29tIiwiaWF0IjoxNjEyMDk5NDg3LCJleHAiOjE2MTIxMDY2ODd9.XjOAE9mPrWQe0ypobpg5AfhGGjE_39Ws29cTAxiTrP0"
        disposable = service.getUsers(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                resultUsers.value = it
            }, {
                it.printStackTrace()
                Log.e(tag, it.printStackTrace().toString())
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    class Factory(
        private val service: ApiService,
        private val pref: SharedPref
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return EditProfilePlayerViewModel(service, pref) as T
        }
    }
}