package com.teamacodechallenge7.ui.loginPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teamacodechallenge7.data.local.SharedPref
import com.teamacodechallenge7.data.remote.ApiService
import com.teamacodechallenge7.data.model.LoginMsg
import com.teamacodechallenge7.data.model.LoginRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginViewModel(private val service: ApiService) : ViewModel() {
    private lateinit var disposable: Disposable
    private val emailResult = MutableLiveData<String>()
    private val passwordResult = MutableLiveData<String>()
    private val buttonResult = MutableLiveData<String>()
    private val resultLogin = MutableLiveData<Boolean>()
    var username: String = ""
    var password: String = ""
    fun emailResult(): LiveData<String> = emailResult
    fun passwordResult(): LiveData<String> = passwordResult
    fun resultLogin(): LiveData<Boolean> = resultLogin
    fun login() {
        when {
            username.isEmpty() && password.isEmpty() -> {
                emailResult.value = "Email tidak boleh kosong!"
                passwordResult.value = "Password tidak boleh kosong!"
                resultLogin.value = false
            }
            username.isEmpty() -> {
                emailResult.value = "Email tidak boleh kosong!"
                resultLogin.value = false
            }
            password.isEmpty() -> {
                passwordResult.value = "Password tidak boleh kosong!"
                resultLogin.value = false
            }
            else -> {
                buttonResult.value = "Loading..."
                val loginRequest = LoginRequest(username, password)
                disposable = service.loginAction(loginRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        SharedPref.id = it.data.id
                        SharedPref.email = it.data.email
                        SharedPref.username = it.data.username
                        SharedPref.token = it.data.token
                        SharedPref.isLogin = true
                        resultLogin.value = true

                    }, { error ->
                        if (error.toString() == "retrofit2.adapter.rxjava2.HttpException: HTTP 401 Unauthorized") {
                            emailResult.value = "Email tidak ada!"
                            resultLogin.value = false
                        } else {
                            passwordResult.value = "Password salah!"
                            resultLogin.value = false
                        }

                    })

            }
        }


    }


}