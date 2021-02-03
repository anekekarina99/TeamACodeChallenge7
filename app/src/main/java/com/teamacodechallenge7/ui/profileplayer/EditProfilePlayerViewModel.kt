package com.teamacodechallenge7.ui.profileplayer

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teamacodechallenge7.data.local.SharedPref
import com.teamacodechallenge7.data.model.Users
import com.teamacodechallenge7.data.remote.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

const val token =
    "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MDE0MjEzMWQ2ODIyYTAwMTdjZGYwYTIiLCJ1c2VybmFtZSI6Imdob3ppbWFuIiwiZW1haWwiOiJnaG96aWNveUBnbWFpbC5jb20iLCJpYXQiOjE2MTIyNjk2MDIsImV4cCI6MTYxMjI3NjgwMn0.5LImP99FkxZlJs0XaWJbEkKdDICmgh9ZoJzyogVnswo"

class EditProfilePlayerViewModel(
    private val service: ApiService,
    private val pref: SharedPref
) : ViewModel() {


    private val tag : String = "EditProfilePlayer"
    private var disposable: CompositeDisposable = CompositeDisposable()
    val resultPost = MutableLiveData<Users>()
    //    private var disposable: Disposable? = null
    var resultUsers = MutableLiveData<Users>()

//    fun playerData() {
////        val token = pref.auth!!
//        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MDE1N2I0OGRiNzg0ODAwMTczZjk4YjkiLCJ1c2VybmFtZSI6ImFndW5ndyIsImVtYWlsIjoiYWd1bmdAeWFob28uY29tIiwiaWF0IjoxNjEyMDk5NDg3LCJleHAiOjE2MTIxMDY2ODd9.XjOAE9mPrWQe0ypobpg5AfhGGjE_39Ws29cTAxiTrP0"
//        disposable = service.getUsers(token)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                resultUsers.value = it
//            }, {
//                it.printStackTrace()
//                Log.e(tag, it.printStackTrace().toString())
//            })
//    }


    fun upload(username: String, email: String, file: File) {
        val filePart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            file.name,
            file.asRequestBody("image/*".toMediaTypeOrNull())
        )

        val usernamePart: RequestBody = username.toRequestBody("multipart/form-data".toMediaType())
        val emailPart: RequestBody = email.toRequestBody("multipart/form-data".toMediaType())

        disposable.addAll(
            service.uploadImage(token, usernamePart, emailPart, filePart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    resultPost.value = it
                }, {
                    it.printStackTrace()
                })
        )
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