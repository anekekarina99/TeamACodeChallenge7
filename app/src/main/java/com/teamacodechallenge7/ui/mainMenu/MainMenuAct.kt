package com.teamacodechallenge7.ui.mainMenu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.teamacodechallenge7.R
import com.teamacodechallenge7.data.local.SharedPref
import com.teamacodechallenge7.data.remote.ApiModule
import com.teamacodechallenge7.databinding.ActivityMainMenuBinding
import com.teamacodechallenge7.ui.about.AboutActivity
import com.teamacodechallenge7.ui.about.InstructionActivity
import com.teamacodechallenge7.ui.gamehistory.GameHistoryAct
import com.teamacodechallenge7.ui.landingPage.LandingPageActivity
import com.teamacodechallenge7.ui.profileplayer.ProfilePlayer
import com.teamacodechallenge7.ui.profileteman.ProfileTeman


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
        binding.viewModel = viewModel
        viewModel.getUser()
        viewModel.imageResult().observe(this, {
            if (!it.isNullOrEmpty()) {
                Glide.with(this)
                    .load(it.toString())
                    .into(binding.IvPhotoUserProfile)
            }

        })
        viewModel.username().observe(this, {
            binding.tvNamaPanjangProfile.text = it.toString()
        })
        binding.btnStart.setOnClickListener {
            startActivity(Intent(this, ChooseGamePlayAct::class.java))
            finish()
        }
        binding.btnMyProfile.setOnClickListener {
            startActivity(Intent(this, ProfilePlayer::class.java))
            finish()
        }
        binding.btnMyFriends.setOnClickListener {
            startActivity(Intent(this, ProfileTeman::class.java))
            finish()
        }
        binding.btnMyScores.setOnClickListener {
            startActivity(Intent(this, GameHistoryAct::class.java))
            finish()
        }
        binding.btnAbout.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
            finish()
        }
        binding.tvSeeInstruction.setOnClickListener {
            startActivity(Intent(this, InstructionActivity::class.java))
            finish()
        }
        binding.btnLogout.setOnClickListener {
            SharedPref.isLogin = false
        }

    }
}