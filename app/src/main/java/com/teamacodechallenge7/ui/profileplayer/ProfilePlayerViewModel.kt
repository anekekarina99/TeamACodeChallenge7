package com.teamacodechallenge7.ui.profileplayer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teamacodechallenge7.data.local.SharedPref

class ProfilePlayerViewModel(
    private val pref: SharedPref
) : ViewModel() {

    var resultName = MutableLiveData<String>()
    var resultEmail = MutableLiveData<String>()
    var resultUrlProfile = MutableLiveData<String>()

    fun playerData() {
        resultName.value = pref.username.toString()
        resultEmail.value = pref.email.toString()
        resultUrlProfile.value = pref.url_profile.toString()
    }

    class Factory(
        private val pref: SharedPref
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProfilePlayerViewModel( pref) as T
        }
    }
}