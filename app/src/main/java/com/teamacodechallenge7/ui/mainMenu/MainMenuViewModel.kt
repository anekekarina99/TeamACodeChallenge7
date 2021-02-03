package com.teamacodechallenge7.ui.mainMenu

import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.teamacodechallenge7.data.model.Users
import com.teamacodechallenge7.data.remote.ApiService
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class MainMenuViewModel(private val service: ApiService) : ViewModel() {
    private lateinit var disposable: Disposable
    private val imageUrl = MutableLiveData<String>()
    private val result = MutableLiveData<Users>()
    fun getImageUrl():String{
        return "http://cdn.meme.am/instances/60677654.jpg"
    }
    private val imageResult = MutableLiveData<String>()
    fun getUser(view: CircleImageView, token: String) {
        disposable = service.getUsers(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result.value = it
            }, {
                /*if (it is HttpException){
                    val msg=it.response().errorBody()?.let { it1->getErrorMessage(it1) }
                }*/
                it.printStackTrace()
            })
    }

    @BindingAdapter("imageUrl")
    fun loadImage(view: CircleImageView, imageUrl: String) {
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(view.context)
                .load(imageUrl)
                .into(view)
        }
       /* else{
            Glide.with(view.context)
                .load(url)
                .into(view)
        }*/
    }

}
