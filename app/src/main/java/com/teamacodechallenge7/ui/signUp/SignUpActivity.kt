package com.teamacodechallenge7.ui.signUp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.teamacodechallenge7.R
import com.teamacodechallenge7.data.remote.ApiModule
import com.teamacodechallenge7.databinding.ActivitySignUpBinding
import com.teamacodechallenge7.ui.loginPage.LoginAct

class SignUpActivity : AppCompatActivity() {
    private lateinit var viewModel: SignUpViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = SignUpFactory(ApiModule.service)
        viewModel = ViewModelProvider(this, factory)[SignUpViewModel::class.java]
        val binding =
            DataBindingUtil.setContentView<ActivitySignUpBinding>(this, R.layout.activity_sign_up)
        binding.viewModel = viewModel
        binding.icBack.setOnClickListener {
            startActivity(Intent(this, LoginAct::class.java))
            finish()
        }
        binding.btnSignUp.setOnClickListener {
            binding.btnSignUp.text = ("Loading...")
            binding.btnSignUp.isEnabled = false
            viewModel.signUp()
        }
        viewModel.resultLogin().observe(this, { bool ->
            if (!bool) {
                finish()
                startActivity(Intent(this, NotifSignUpActivity::class.java))
                binding.btnSignUp.isEnabled = bool
            } else {
                binding.btnSignUp.isEnabled = bool
                viewModel.buttonResult().observe(this, { but ->
                    binding.btnSignUp.text = but
                    viewModel.errorMsg().observe(this, { errMsg ->
                        viewModel.typeError().observe(this, { typeErr ->
                            when (typeErr) {
                                "email" -> {
                                    binding.etEmail.error = errMsg
                                    binding.etUsername.error = null
                                    binding.etPassword.error = null
                                }
                                "username" -> {
                                    binding.etUsername.error = errMsg
                                    binding.etEmail.error = null
                                    binding.etPassword.error = null
                                }
                                "password" -> {
                                    binding.etPassword.error = errMsg
                                    binding.etUsername.error = null
                                    binding.etEmail.error = null
                                }
                                "repassword" -> {
                                    binding.etRePassword.error = errMsg
                                }
                            }
                        })

                    })

                })

            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, LoginAct::class.java))
        finish()
    }
}