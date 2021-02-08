package com.teamacodechallenge7.ui.mainMenu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.teamacodechallenge7.R
import com.teamacodechallenge7.databinding.ActivityChooseGamePlayBinding
import com.teamacodechallenge7.ui.pilihlawan.PilihLawan
import com.teamacodechallenge7.ui.playgamevscomputer.PlayGameVsComputer
import com.teamacodechallenge7.utils.GameMusic

class ChooseGamePlayAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startService(Intent(this, GameMusic::class.java))
        val binding =
            DataBindingUtil.setContentView<ActivityChooseGamePlayBinding>(
                this,
                R.layout.activity_choose_game_play
            )
        binding.btPlayOther.setOnClickListener {
            stopMusic()
            startActivity(Intent(this, PilihLawan::class.java))
            finish()
        }
        binding.btPlayComputer.setOnClickListener {
            stopMusic()
            startActivity(Intent(this, PlayGameVsComputer::class.java))
            finish()
        }
        binding.btBackMenu.setOnClickListener {
            startActivity(Intent(this, MainMenuAct::class.java))
            finish()
        }
    }

    private fun stopMusic() {
        stopService(Intent(this, GameMusic::class.java))
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainMenuAct::class.java))
        finish()
    }

}