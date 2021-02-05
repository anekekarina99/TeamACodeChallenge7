package com.teamacodechallenge7.ui.skor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamacodechallenge7.R
import com.teamacodechallenge7.data.local.SharedPref
import com.teamacodechallenge7.data.remote.ApiModule
import com.teamacodechallenge7.ui.mainMenu.MainMenuAct

class Skor : AppCompatActivity() {
    private val tag : String = "Skor"
    private lateinit var skorViewModel: SkorViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skor)

        val pref = SharedPref

        val factory = SkorViewModel.Factory(ApiModule.service, pref)
        skorViewModel = ViewModelProvider(this, factory)[SkorViewModel::class.java]

        val ivBack = findViewById<ImageView>(R.id.ivBack)
        val ivProfile = findViewById<ImageView>(R.id.ivProfile)
        recyclerView = findViewById(R.id.recyclerView)

        Glide
            .with(this)
            .load(pref.url_profile)
            .centerCrop()
            .circleCrop()
            .placeholder(R.drawable.ic_people)
            .into(ivProfile);

        fetchData()

        ivBack.setOnClickListener {
            startActivity(Intent(this, MainMenuAct::class.java))
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    fun fetchData() {
        Log.e(tag,"fetchData")
//        recyclerView?.let { skorViewModel.listSkor(it, this) }
        skorViewModel.listSkor(recyclerView, this)
        recyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )
    }
}