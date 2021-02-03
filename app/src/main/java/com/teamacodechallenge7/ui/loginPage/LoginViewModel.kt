package com.teamacodechallenge7.ui.loginPage

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teamacodechallenge7.data.local.SharedPref
import com.teamacodechallenge7.data.remote.ApiService
import com.teamacodechallenge7.data.model.LoginRequest
import com.teamacodechallenge7.utils.getServiceErrorMsg
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
    fun buttonResult(): LiveData<String> = buttonResult
    fun passwordResult(): LiveData<String> = passwordResult
    fun resultLogin(): LiveData<Boolean> = resultLogin
    fun login() {
        mutableListOf(username, password).forEachIndexed { index, s ->
            when {
                s.isEmpty() -> {
                    when (index) {
                        0 -> {
                            emailResult.value = "Email tidak boleh kosong!"
                            resultLogin.value = false
                            buttonResult.value = "Signin"
                        }
                        1 -> {
                            passwordResult.value = "Password tidak boleh kosong!"
                            resultLogin.value = false
                            buttonResult.value = "Signin"
                        }
                    }
                }
                index == 0 -> {
                    if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                        emailResult.value = "Email tidak valid!"
                        resultLogin.value = false
                        buttonResult.value = "Signin"
                    }
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
                            SharedPref.password = password
                            SharedPref.isLogin = true
                            resultLogin.value = true
                        }, {
                            val msg = it.getServiceErrorMsg()
                            Log.e("Opo error e?", msg)
                            when {
                                msg.contains("Wrong password!") -> {
                                    passwordResult.value = "Password salah!"
                                    resultLogin.value = false
                                    buttonResult.value = "Signin"
                                }
                                msg.contains("Email doesn't exist!") -> {
                                    emailResult.value = "Email tidak ada!"
                                    resultLogin.value = false
                                    buttonResult.value = "Signin"
                                }
                            }
                        })
                }

            }
        }

    }
}
