package com.teamacodechallenge7.ui.skor

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.teamacodechallenge7.data.local.SharedPref
import com.teamacodechallenge7.data.model.GetBattle
import com.teamacodechallenge7.data.remote.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SkorViewModel(
    private val service: ApiService,
    private val pref: SharedPref
) : ViewModel() {

    val tag = "Skor"
    private var disposable: Disposable? = null
    val token = pref.token!!
    val resultMe = MutableLiveData<GetBattle>()
//    lateinit var resultMe : List<GetBattle>

    fun listSkor(recyclerView: RecyclerView, context: Context) {
        disposable = service.getBattle(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                resultMe.value = it
            }, {
                it.printStackTrace()
            })
        Log.e(tag, resultMe.toString())
//        Toast.makeText(context, it.toString(),Toast.LENGTH_SHORT).show()
//        recyclerView.layoutManager = LinearLayoutManager(
//            context, LinearLayoutManager.VERTICAL, false
//        )
//        val adapter = SkorAdapter(resultMe, context)
//        adapter.notifyDataSetChanged()
//        recyclerView.adapter = adapter
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