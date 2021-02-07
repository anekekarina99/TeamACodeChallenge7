package com.teamacodechallenge7.ui.about

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.teamacodechallenge7.R
import com.teamacodechallenge7.databinding.ActivityAboutBinding
import com.teamacodechallenge7.ui.landingPage.LandingPageActivity
import com.teamacodechallenge7.ui.mainMenu.MainMenuAct

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, MainMenuAct::class.java))
            finish()
        }
    }
}