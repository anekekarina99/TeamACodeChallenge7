package com.teamacodechallenge7.ui.about

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.teamacodechallenge7.databinding.ActivityAboutBinding
import com.teamacodechallenge7.ui.profileplayer.ProfilePlayer

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, ProfilePlayer::class.java))
            finish()
        }
    }
}