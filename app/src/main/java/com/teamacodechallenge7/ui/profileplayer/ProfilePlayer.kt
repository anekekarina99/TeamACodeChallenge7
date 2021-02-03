package com.teamacodechallenge7.ui.profileplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.teamacodechallenge7.R
import com.teamacodechallenge7.data.local.SharedPref
import com.teamacodechallenge7.ui.mainMenu.MainMenuAct

class ProfilePlayer : AppCompatActivity() {
    private val tag : String = "ProfilePlayer"
    private lateinit var profilePlayerViewModel: ProfilePlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_player)

        val pref = SharedPref
        val factory = ProfilePlayerViewModel.Factory( pref)
        profilePlayerViewModel = ViewModelProvider(this, factory)[ProfilePlayerViewModel::class.java]

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
        profilePlayerViewModel.resultName.observe(this) {
            tvName.text = it
        }
        profilePlayerViewModel.resultEmail.observe(this) {
            tvEmail.text = it
        }
        profilePlayerViewModel.resultUrlProfile.observe(this) {
            Glide
                .with(this)
                .load(it)
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
        profilePlayerViewModel.playerData()
    }
}