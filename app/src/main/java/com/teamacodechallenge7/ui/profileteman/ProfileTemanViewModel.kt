package com.teamacodechallenge7.ui.profileteman

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.teamacodechallenge6.data.database.Teman
import com.teamacodechallenge6.data.database.TemanDatabase
import com.teamacodechallenge7.data.local.SharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileTemanViewModel() : ViewModel() {

    val resultName = MutableLiveData<String>()

    var mDB: TemanDatabase?=null
    fun playerName() {
//        mDB = context.get()?.let { TemanDatabase.getInstance(it) }
//        GlobalScope.launch(Dispatchers.IO) {
//            val pemain = SharedPref.id?.let { mDB?.pemainDao()?.getPemainById(it) } // nanti dari retrofit
//            launch(Dispatchers.Main) {
//                val username = pemain?.username.toString()
//                val email = pemain?.email.toString()
//                view.nameEmail(username, email)
                resultName.value = "Agung"
//            }
        }
//    }

    fun addTeman(name: String, email: String) {
//        mDB = context.get()?.let { TemanDatabase.getInstance(it) }
        val id = SharedPref.id
        val objectTeman = id?.let { Teman(null, it, name, email) }
        GlobalScope.launch(Dispatchers.IO) {
//            val result = objectTeman?.let { mDB?.temanDao()?.insertTeman(it) }
            launch(Dispatchers.Main) {
//                if (result != 0.toLong()) {
//                    view.onSuccessTeman("Teman kamu $name berhasil ditambahakan")
//                } else {
//                    view.onFailedTeman("Teman kamu $name gagal ditambahakan")
                }
            }

        }
//    }

    fun listTeman(recyclerView: RecyclerView, context: Context) {
        val id = SharedPref.id
//        mDB = App.context.get()?.let { TemanDatabase.getInstance(it) }
        GlobalScope.launch(Dispatchers.IO) {
//            val listTeman = id?.let { mDB?.temanDao()?.getAllbyId(it) }
            launch(Dispatchers.Main) {
//                listTeman?.let {
//                    val adapter = TemanAdapter(listTeman, context)
//                    recyclerView.adapter = adapter
                }
            }
        }
//    }

    fun deleteTeman(list: List<Teman>, position: Int) {
        GlobalScope.launch(Dispatchers.IO) {
//            val result = mDB?.temanDao()?.deleteTeman(list[position])
            launch(Dispatchers.Main) {
//                if (result != 0) {
//                    view.onSuccessTeman("Teman kamu berhasil dihapus")
//                } else {
//                    view.onFailedTeman("Teman kamu gagal dihapus")
//                }
            }
        }
    }

    fun editTeman(list: List<Teman>, position: Int) {
        GlobalScope.launch(Dispatchers.IO) {
//            val result = mDB?.temanDao()?.updateTeman(list[position])
            launch(Dispatchers.Main) {
//                if (result != 0) {
//                    view.onSuccessTeman("Teman kamu berhasil diubah")
//                } else {
//                    view.onSuccessTeman("Teman kamu gagal diubah")
//                }
            }
        }
    }


    fun destroyDB() {
        TemanDatabase.destroyInstance()
    }

    class Factory() : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProfileTemanViewModel() as T
        }
    }
}