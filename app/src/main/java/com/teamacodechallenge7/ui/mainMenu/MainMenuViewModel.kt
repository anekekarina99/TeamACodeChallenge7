package com.teamacodechallenge7.ui.mainMenu

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.teamacodechallenge7.data.model.GetUsers
import com.teamacodechallenge7.data.remote.ApiService
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException


class MainMenuViewModel(private val service: ApiService) : ViewModel() {
    private lateinit var disposable: Disposable
    private val result = MutableLiveData<GetUsers>()
    private val imageResult = MutableLiveData<String>()
    fun imageResult(): LiveData<String> = imageResult
    fun getUser(view: CircleImageView, token: String) {
        disposable = service.getUsers(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result.value = it
                loadImage(view,it.data.photo)
            }, {
                /*if (it is HttpException){
                    val msg=it.response().errorBody()?.let { it1->getErrorMessage(it1) }
                }*/
                it.printStackTrace()
            })
    }
    fun gatelImage(){

    }
    @BindingAdapter("imageUrl")
    fun loadImage(view: CircleImageView, url: String) {
        Glide.with(view.context)
            .load(url)
            .into(view)
       /* if (!url.isNullOrEmpty()) {
            Glide.with(view.context)
                .load(url)
                .into(view)
        }
        else{
            Glide.with(view.context)
                .load(url)
                .into(view)
        }*/
    }

}