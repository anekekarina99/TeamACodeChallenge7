package com.teamacodechallenge7.ui.profileplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.teamacodechallenge7.R
import com.teamacodechallenge7.data.local.SharedPref
import com.teamacodechallenge7.data.remote.ApiModule

class EditProfilePlayer : AppCompatActivity() {
    private val tag : String = "EditProfilePlayer"
    private lateinit var editProfilePlayerViewModel: EditProfilePlayerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_player)

        val pref = SharedPref
        val factory = EditProfilePlayerViewModel.Factory(ApiModule.service, pref)
        editProfilePlayerViewModel = ViewModelProvider(this, factory)[EditProfilePlayerViewModel::class.java]

        val btSave = findViewById<Button>(R.id.btSave)
        val btClose = findViewById<ImageView>(R.id.btClose)
        val ivProfile = findViewById<ImageView>(R.id.ivProfile)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        fetchData()

        btClose.setOnClickListener {
            val intent = Intent(this, ProfilePlayer::class.java)
            startActivity(intent)
        }
        btSave.setOnClickListener {
//            val intent = Intent(this, EditProfilePlayer::class.java)
//            startActivity(intent)
        }
        editProfilePlayerViewModel.resultUsers.observe(this) {

            Log.e(tag, it.toString())
            etUsername.hint = it.data.username
            etEmail.hint = it.data.email
            Glide
                .with(this)
                .load(it.data.photo)
                .centerCrop()
                .circleCrop()
                .placeholder(R.drawable.ic_people)
                .into(ivProfile);
        }

    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    fun fetchData(){
        editProfilePlayerViewModel.playerData()
    }
}