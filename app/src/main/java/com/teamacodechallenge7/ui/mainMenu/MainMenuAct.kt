package com.teamacodechallenge7.ui.mainMenu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.teamacodechallenge7.R
import com.teamacodechallenge7.data.remote.ApiModule
import com.teamacodechallenge7.data.repository.MainMenuFactory
import com.teamacodechallenge7.databinding.ActivityMainMenuBinding
import com.teamacodechallenge7.ui.about.AboutActivity
import com.teamacodechallenge7.ui.profileplayer.ProfilePlayer
import com.teamacodechallenge7.ui.profileteman.ProfileTeman
import com.teamacodechallenge7.ui.skor.Skor


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
       viewModel.username().observe(this,{
            binding.tvNamaPanjangProfile.text=it.toString()
        })
        viewModel.email().observe(this,{
            binding.tvEmailMainMenuProfile.text=it.toString()
        })
        binding.btnStart.setOnClickListener {
          /*  startActivity(Intent(this, MainMenuAct::class.java))*/
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
            startActivity(Intent(this, Skor::class.java))
            finish()
        }
        binding.btnAbout.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
            finish()
        }
        binding.tvSeeInstruction.setOnClickListener {

        }
        binding.btnLogout.setOnClickListener {

        }

    }
}