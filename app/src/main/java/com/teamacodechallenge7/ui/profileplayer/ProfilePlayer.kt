package com.teamacodechallenge7.ui.profileplayer

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.teamacodechallenge7.R
import com.teamacodechallenge7.data.local.SharedPref
import com.teamacodechallenge7.ui.loginPage.LoginAct
import com.teamacodechallenge7.ui.mainMenu.MainMenuAct
import java.util.*
import kotlin.concurrent.schedule
import kotlin.concurrent.timerTask

class ProfilePlayer : AppCompatActivity() {
    private val tag: String = "ProfilePlayer"
    private lateinit var profilePlayerViewModel: ProfilePlayerViewModel
    private lateinit var lParent: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_player)

        val pref = SharedPref
        val factory = ProfilePlayerViewModel.Factory(pref)
        profilePlayerViewModel =
            ViewModelProvider(this, factory)[ProfilePlayerViewModel::class.java]

        lParent = findViewById(R.id.lParent)
        val btEdit = findViewById<Button>(R.id.btEdit)
        val ivBack = findViewById<ImageView>(R.id.ivBack)
        val ivProfile = findViewById<ImageView>(R.id.ivProfile)
        val tvName = findViewById<TextView>(R.id.tvName)
        val tvEmail = findViewById<TextView>(R.id.tvEmail)

        fetchData()

        ivBack.setOnClickListener {
            startActivity(Intent(this, MainMenuAct::class.java))
            finish()
        }
        btEdit.setOnClickListener {
            val intent = Intent(this, EditProfilePlayer::class.java)
            startActivity(intent)
            finish()
        }
        profilePlayerViewModel.resultUser.observe(this) {
            tvName.text = it.data.username
            tvEmail.text = it.data.email
            btEdit.text = "Edit Profile"
            Glide
                .with(this)
                .load(it.data.photo)
                .centerCrop()
                .circleCrop()
                .placeholder(R.drawable.ic_people)
                .into(ivProfile);
        }
        profilePlayerViewModel.resultMessage.observe(this) {
            Log.e(tag, it.toString())
            if (it.equals("Token is expired") || it.equals("Invalid Token")) {
                val snackbar = Snackbar.make(
                    lParent,
                    "Waktu bermain sudah selesai, main lagi? silahkan Login",
                    Snackbar.LENGTH_INDEFINITE
                )
                snackbar.setAction("Login") {
                    snackbar.dismiss()
                    startActivity(Intent(this, LoginAct::class.java))
                    finish()
                }.show()
            }
        }

        //=================== simpan Login datetime (ini ditaruh saat login)========================
        var calendar = Calendar.getInstance()
        calendar.add(Calendar.SECOND, 10) // second nya diganti Hour, yg ini buat nyoba aj
        var dLogin: Long = calendar.time.time
        pref.datetime_login = dLogin.toString()
        Log.e(tag, dLogin.toString())


        //=================== simpan Login datetime (ini ditaruh di App)========================
        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                var runCalendar = Calendar.getInstance()
                var rTimer = runCalendar.time.time
                Log.e(tag, pref.datetime_login + " - " + rTimer.toString())

                if ((pref.datetime_login)!!.toLong() < rTimer) {
                    Log.e(tag, "waktunya Login")
                }
                mainHandler.postDelayed(this, 1000)
            }
        })

        //=================== simpan Login datetime (ini ditaruh di App)========================

    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    fun fetchData() {
        profilePlayerViewModel.playerData()
    }
}