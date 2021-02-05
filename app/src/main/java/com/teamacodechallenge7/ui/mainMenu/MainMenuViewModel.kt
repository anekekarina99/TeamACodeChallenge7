package com.teamacodechallenge7.ui.mainMenu

import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.teamacodechallenge7.R
import com.teamacodechallenge7.data.local.SharedPref
import com.teamacodechallenge7.data.model.Users
import com.teamacodechallenge7.data.remote.ApiService

import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class MainMenuViewModel(private val service: ApiService) : ViewModel() {
    private lateinit var disposable: Disposable
    private val username = MutableLiveData<String>()
    private val imageResult = MutableLiveData<String>()
    fun imageResult(): LiveData<String> = imageResult
    fun username():LiveData<String> = username
    fun getUser() {
       val  token= SharedPref.token.toString()
        disposable = service.getUsers(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                imageResult.value=it.data.photo
                username.value=("Hi, ${it.data.username}")
            }, {
                it.printStackTrace()
            })
    }


}


