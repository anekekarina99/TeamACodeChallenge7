package com.teamacodechallenge7.ui.playgamevsplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.teamacodechallenge7.R
import com.teamacodechallenge7.data.local.SharedPref
import com.teamacodechallenge7.data.remote.ApiModule
import com.teamacodechallenge7.databinding.ActivityPlayGameVsPlayerBinding
import com.teamacodechallenge7.ui.mainMenu.ChooseGamePlayAct
import com.teamacodechallenge7.utils.GamePlayMusic
import com.teamacodechallenge7.utils.SoundPlayer

class PlayGameVsPlayer : AppCompatActivity() {
    private lateinit var viewModel: PlayGameVsPlayerViewModel
    private lateinit var sound: SoundPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pref = SharedPref
        val factory = PlayGameVsPlayerViewModel.Factory(ApiModule.service, pref)
        sound = SoundPlayer(this)
        viewModel = ViewModelProvider(this, factory)[PlayGameVsPlayerViewModel::class.java]
        viewModel.teman = intent.getStringExtra("NAMA_TEMAN").toString()
        val binding =
            DataBindingUtil.setContentView<ActivityPlayGameVsPlayerBinding>(
                this,
                R.layout.activity_play_game_vs_player
            )
        binding.viewModel = viewModel
        val pemain1 = mutableListOf(
            binding.ivBatu,
            binding.ivGunting,
            binding.ivKertas
        )
        val pemain2 = mutableListOf(
            binding.ivBatuLawan,
            binding.ivGuntingLawan,
            binding.ivKertasLawan
        )
        val pilihan = mutableListOf("batu", "gunting", "kertas")
        var skor = 0
        var skorLawan = 0

        pemain1.forEach {
            it.setOnClickListener {it1->
                it1.setBackgroundResource(R.drawable.bg_box_blue_round)
                viewModel.pilihan = pilihan[pemain1.indexOf(it1)]
                pemain1.forEach {it2->
                    it2.isClickable = false
                    it2.visibility = View.INVISIBLE
                }
                pemain2.forEach {it3->
                    it3.visibility = View.VISIBLE
                }
            }
        }

        pemain2.forEach {
            it.setOnClickListener {it1->
                it1.setBackgroundResource(R.drawable.bg_box_blue_round)
                viewModel.pilihanLawan = pilihan[pemain2.indexOf(it)]
                it.setBackgroundResource(R.drawable.bg_box_blue_round)
                viewModel.play()
                pemain2.forEach {it2->
                    it2.isClickable = false
                    it2.visibility = View.INVISIBLE
                }
            }
        }

        viewModel.result().observe(this, { result ->
            viewModel.simpanBattle()
            stopMusic()
            val view = LayoutInflater.from(this).inflate(R.layout.result_game_dialog, null, false)
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setView(view)
            val dialogD1 = dialogBuilder.create()
            dialogD1.setCancelable(false)
            sound.playGameSound()
            dialogD1.show()
            val animation by lazy { view.findViewById<LottieAnimationView>(R.id.lav_success) }
            val winnerInfo by lazy { view.findViewById<TextView>(R.id.winner) }
            val playAgain by lazy { view.findViewById<Button>(R.id.play_again) }
            val backMenu by lazy { view.findViewById<Button>(R.id.back_menu) }
            winnerInfo.text = result

            //View animation
            animation.speed = 0.5F
            animation.repeatCount = 5
            animation.repeatMode = LottieDrawable.RESTART
            animation.playAnimation()

            playAgain.setOnClickListener {
                startService(Intent(this, GamePlayMusic::class.java))
                pemain1.forEach {
                    it.isClickable = true
                    it.setBackgroundResource(R.drawable.bg_box_white_round)
                    it.visibility = View.VISIBLE
                }
                pemain2.forEach {
                    it.isClickable = true
                    it.setBackgroundResource(R.drawable.bg_box_white_round)
                    it.visibility = View.INVISIBLE
                }
                dialogD1.dismiss()
            }
            backMenu.setOnClickListener {
                stopMusic()
                val intent = Intent(this, ChooseGamePlayAct::class.java)
                startActivity(intent)
                finish()

            }
            if (viewModel.skor == 1) {
                skor++
                binding.tvSkor.text = skor.toString()
            }
            if (viewModel.skor == 2) {
                skorLawan++
                binding.tvSkorLawan.text = skorLawan.toString()
            }
        })

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, ChooseGamePlayAct::class.java))
            stopMusic()
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        stopMusic()
        startActivity(Intent(this, ChooseGamePlayAct::class.java))
        finish()
    }
    private fun stopMusic() {
        stopService(Intent(this, GamePlayMusic::class.java))
    }
}