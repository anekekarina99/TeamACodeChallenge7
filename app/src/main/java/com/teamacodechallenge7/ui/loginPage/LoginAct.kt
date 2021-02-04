package com.teamacodechallenge7.ui.loginPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.teamacodechallenge7.R
import com.teamacodechallenge7.data.remote.ApiModule
import com.teamacodechallenge7.data.repository.LoginFactory
import com.teamacodechallenge7.databinding.ActivityLoginBinding
import com.teamacodechallenge7.ui.mainMenu.MainMenuAct
import com.teamacodechallenge7.ui.signUp.SignUpActivity

class LoginAct : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding =
            DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        val factory = LoginFactory(ApiModule.service)
        this.viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.btSignIn.setOnClickListener {
            binding.btSignIn.text=("Loading...")
            binding.btSignIn.isEnabled=false
            viewModel.login()
        }
        binding.btSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
        viewModel.resultLogin().observe(this, {
            if (!it) {
                startActivity(Intent(this, MainMenuAct::class.java))
                finish()
                binding.btSignIn.isEnabled=it
            } else {
                viewModel.buttonResult().observe(this,{but->
                    binding.btSignIn.text=but
                    viewModel.emailResult().observe(this, { emailErr ->
                        binding.etEmail.error = emailErr
                        binding.btSignIn.isEnabled=it
                    })
                    viewModel.passwordResult().observe(this, { passErr ->
                        binding.etPassword.error = passErr
                        binding.btSignIn.isEnabled=it
                    })
                })


            }
        })
    }

}
