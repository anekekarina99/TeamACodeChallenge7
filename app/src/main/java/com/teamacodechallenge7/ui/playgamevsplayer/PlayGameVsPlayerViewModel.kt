package com.teamacodechallenge7.ui.playgamevsplayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teamacodechallenge7.data.local.SharedPref
import com.teamacodechallenge7.data.model.PostBattleRequest
import com.teamacodechallenge7.data.remote.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PlayGameVsPlayerViewModel(private val service: ApiService, private val pref: SharedPref) :
    ViewModel() {

    private val token = pref.token.toString()
    private lateinit var disposable: Disposable
    private val result = MutableLiveData<String>()
    var username: String = pref.username.toString()
    var teman: String = ""
    var pilihan = ""
    var pilihanLawan = ""
    fun result(): LiveData<String> = result
    var skor = 0


    fun play() {
        if (pilihan == pilihanLawan) {
            result.value = "Draw"
        } else {
            if (pilihan == "gunting" && pilihanLawan == "kertas" || pilihan == "kertas" && pilihanLawan == "batu" || pilihan == "batu" && pilihanLawan == "gunting") {
                skor = 1
                result.value = "${username} \n WIN!"
            } else {
                skor = 2
                result.value = "${teman} \n WIN!"
            }
        }
    }

    fun simpanBattle() {
        var hasil = ""
        if (skor == 1){
            hasil = "Player Win"
        }
        else if(skor == 2){
            hasil = "Opponent Win"
        }
        else{
            hasil = "Draw"
        }
        val postBattleRequest= PostBattleRequest("Multiplayer", hasil)
        disposable = service.postBattle(token, postBattleRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it
            }, {
            })
    }



    class Factory(private val service: ApiService, private val pref: SharedPref) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PlayGameVsPlayerViewModel(service, pref) as T
        }

    }

}