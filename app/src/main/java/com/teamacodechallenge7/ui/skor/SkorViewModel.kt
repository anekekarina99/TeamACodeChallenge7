package com.teamacodechallenge7.ui.skor

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamacodechallenge7.data.local.SharedPref
import com.teamacodechallenge7.data.model.GetBattle
import com.teamacodechallenge7.data.remote.ApiService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SkorViewModel(
    private val service: ApiService,
    private val pref: SharedPref
) : ViewModel() {

    val tag = "Skor"
    private var disposable: Disposable? = null
//    val token = pref.token
    val token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MDE1N2I0OGRiNzg0ODAwMTczZjk4YjkiLCJ1c2VybmFtZSI6ImFndW5ndyIsImVtYWlsIjoiYWd1bmd3QHlhaG9vLmNvbSIsImlhdCI6MTYxMjM1NzQxMCwiZXhwIjoxNjEyMzY0NjEwfQ.Knn2frT7Wnvldl6iNmSEI5ec3yJlO5N1h2cU-MvDdC8"

    var reslt = MutableLiveData<List<GetBattle.Data>>()

    fun listSkor(recyclerView: RecyclerView, context: Context) {
        recyclerView.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )
        disposable = service.getBattle(pref.token.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
//                resultMe = it
                val adapter = SkorAdapter(it, context)
                adapter.notifyDataSetChanged()
                recyclerView.adapter = adapter
            }, {
                it.printStackTrace()
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
            return SkorViewModel(service, pref) as T
        }
    }
}