package com.teamacodechallenge7.ui.loginPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.teamacodechallenge7.R
import com.teamacodechallenge7.data.model.LoginRequest
import com.teamacodechallenge7.data.remote.ApiModule
import com.teamacodechallenge7.data.repository.LoginFactory
import com.teamacodechallenge7.databinding.ActivityLoginBinding
import com.teamacodechallenge7.ui.mainMenu.MainMenuAct

class LoginAct : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        val factory = LoginFactory(ApiModule.service)
        this.viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
        val binding =
            DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.btSignIn.setOnClickListener {
            viewModel.login()
        }
        viewModel.resultLogin().observe(this,{ it ->
            if(it){
                startActivity(Intent(this,MainMenuAct::class.java))
                finish()
            }
            else{
                viewModel.emailResult().observe(this, {emailErr->
                    binding.etEmail.error = emailErr
                })
                viewModel.passwordResult().observe(this, Observer {passErr->
                    binding.etPassword.error = passErr
                })
                binding.btSignIn.isEnabled = true
                binding.btSignIn.text = ("Signin")
            }

        })
    }

}
