package com.teamacodechallenge7.ui.signUp

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teamacodechallenge7.data.remote.ApiService
import com.teamacodechallenge7.data.model.SignUpRequest
import com.teamacodechallenge7.utils.getServiceErrorMsg
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.regex.Pattern

class SignUpViewModel(private val service: ApiService) : ViewModel() {
    private val usernameRegex =
        Pattern.compile("^(?=.{6,20}\$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])\$")
    private var disposable: Disposable? = null
    private val emailResult = MutableLiveData<String>()
    private val passwordResult = MutableLiveData<String>()
    private val rePasswordResult = MutableLiveData<String>()
    private val usernameResult = MutableLiveData<String>()
    private val buttonResult = MutableLiveData<String>()
    private val resultSignUp = MutableLiveData<Boolean>()
    var username: String = ""
    var password: String = ""
    var email: String = ""
    var rePassword: String = ""
    fun emailResult(): LiveData<String> = emailResult
    fun buttonResult(): LiveData<String> = buttonResult
    fun passwordResult(): LiveData<String> = passwordResult
    fun resultLogin(): LiveData<Boolean> = resultSignUp
    fun usernameResult(): LiveData<String> = usernameResult
    fun rePasswordResult(): LiveData<String> = rePasswordResult

    fun signUp() {
        mutableListOf(username, email, password, rePassword).forEachIndexed { index, s ->
            when {
                s.isEmpty() -> {
                    when (index) {
                        0 -> {
                            usernameResult.value = "Email tidak boleh kosong!"
                            resultSignUp.value = true
                            buttonResult.value = "Signup"
                        }
                        1 -> {
                            emailResult.value = "Email tidak boleh kosong!"
                            resultSignUp.value = true
                            buttonResult.value = "Signup"
                        }
                        2 -> {
                            passwordResult.value = "Password tidak boleh kosong!"
                            resultSignUp.value = true
                            buttonResult.value = "Signup"
                        }
                        3 -> {
                            rePasswordResult.value = "Re-Password tidak boleh kosong!"
                            resultSignUp.value = true
                            buttonResult.value = "Signup"
                        }
                    }
                }
                index == 0 -> {
                    if (!usernameRegex.matcher(username).matches()) {
                        usernameResult.value = "Harus lebih dari 5 (a-z / 0-9)"
                        resultSignUp.value = true
                        buttonResult.value = "Signup"
                    }
                }
                index == 1 -> {
                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        emailResult.value = "Email tidak valid!"
                        resultSignUp.value = true
                        buttonResult.value = "Signup"
                    }
                }
                else -> {
                    if (password == rePassword) {
                        val data = SignUpRequest(email, password, username)
                        disposable = service.signUp(data)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                if (it.success == true) {
                                    resultSignUp.value = false
                                }
                            }, {
                                val msg = it.getServiceErrorMsg()
                                when {
                                    msg.contains("username_1 dup key") -> {
                                        usernameResult.value = "Username telah digunakan"
                                        resultSignUp.value = true
                                        buttonResult.value = "Signup"
                                    }
                                    msg.contains("email_1 dup key") -> {
                                        emailResult.value = "Email telah digunakan"
                                        resultSignUp.value = true
                                        buttonResult.value = "Signup"
                                    }
                                }
                            })
                    } else {
                        rePasswordResult.value = "Re-Password berbeda dengan password"
                        resultSignUp.value = true
                        buttonResult.value = "Signup"
                    }
                }
            }
        }
    }

}