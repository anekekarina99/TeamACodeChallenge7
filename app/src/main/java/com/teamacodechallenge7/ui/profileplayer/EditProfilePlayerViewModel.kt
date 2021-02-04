package com.teamacodechallenge7.ui.profileplayer

import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teamacodechallenge7.data.local.SharedPref
import com.teamacodechallenge7.data.model.Users
import com.teamacodechallenge7.data.remote.ApiService
import com.teamacodechallenge7.utils.App.Companion.context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import com.teamacodechallenge7.utils.getServiceErrorMsg
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class EditProfilePlayerViewModel(
    private val service: ApiService,
    private val pref: SharedPref
) : ViewModel() {

    val token = pref.token.toString()
//        "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MDE1N2I0OGRiNzg0ODAwMTczZjk4YjkiLCJ1c2VybmFtZSI6ImFndW5ndyIsImVtYWlsIjoiYWd1bmd3QHlhaG9vLmNvbSIsImlhdCI6MTYxMjM1NzQxMCwiZXhwIjoxNjEyMzY0NjEwfQ.Knn2frT7Wnvldl6iNmSEI5ec3yJlO5N1h2cU-MvDdC8eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MDE1N2I0OGRiNzg0ODAwMTczZjk4YjkiLCJ1c2VybmFtZSI6ImFndW5ndyIsImVtYWlsIjoiYWd1bmd3QHlhaG9vLmNvbSIsImlhdCI6MTYxMjM1NzQxMCwiZXhwIjoxNjEyMzY0NjEwfQ.Knn2frT7Wnvldl6iNmSEI5ec3yJlO5N1h2cU-MvDdC8"

    private val tag: String = "EditProfilePlayer"
    private var disposable: CompositeDisposable = CompositeDisposable()
    val resultPost = MutableLiveData<Boolean>()
    var resultName = MutableLiveData<String>()
    var resultEmail = MutableLiveData<String>()
    var resultUrlProfile = MutableLiveData<String>()
    var resultMessage = MutableLiveData<String>()

    fun playerData() {
        resultName.value = pref.username
        resultEmail.value = pref.email
        resultUrlProfile.value = pref.url_profile
    }

    fun upload(username: String, email: String, file: File) {
        Log.e(tag, "upload?")
        if (username.length < 6) {
            resultMessage.value = "Username paling sedikit 6 huruf"
        } else if (email.isEmpty()) {
            resultMessage.value = "Email tidak boleh kosong"
        } else if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            resultMessage.value = "Email tidak valid"
        } else {

            val filePart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                file.asRequestBody("image/*".toMediaTypeOrNull())
            )

            val usernamePart: RequestBody =
                username.toRequestBody("multipart/form-data".toMediaType())
            val emailPart: RequestBody = email.toRequestBody("multipart/form-data".toMediaType())

            disposable.addAll(
                service.uploadImage(token, usernamePart, emailPart, filePart)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        resultPost.value = true
                        pref.email = it.data.email
                        pref.username = it.data.username
                        pref.url_profile = it.data.photo
                        Log.e(tag, "datasaved")
                        resultMessage.value = "data diperbaharui"
                    }, {
                        val msg: String = it.getServiceErrorMsg()
                        Log.e(tag, msg)
                        if (msg.equals("Token is expired")|| msg.equals("Invalid Token") ) {
                            resultMessage.value = msg
                        }
                        it.printStackTrace()
                    })
            )

        }
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
