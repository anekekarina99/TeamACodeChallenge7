package com.teamacodechallenge7.ui.mainMenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.teamacodechallenge7.R
import com.teamacodechallenge7.data.remote.ApiModule
import com.teamacodechallenge7.data.repository.MainMenuFactory
import com.teamacodechallenge7.databinding.ActivityMainMenuBinding


class MainMenuAct : AppCompatActivity() {
    private lateinit var viewModel: MainMenuViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainMenuBinding>(
            this,
            R.layout.activity_main_menu
        )
        val factory = MainMenuFactory(ApiModule.service)
        this.viewModel = ViewModelProvider(this, factory)[MainMenuViewModel::class.java]
        binding.btnStart.setOnClickListener {

        }
        binding.btnMyProfile.setOnClickListener {

        }
        binding.btnMyFriends.setOnClickListener {

        }
        binding.btnMyScores.setOnClickListener {

        }
        binding.btnAbout.setOnClickListener {

        }
        binding.tvSeeInstruction.setOnClickListener {

        }
        binding.btnLogout.setOnClickListener {

        }

    }
}