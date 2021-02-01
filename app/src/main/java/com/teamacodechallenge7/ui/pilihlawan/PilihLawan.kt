package com.teamacodechallenge7.ui.pilihlawan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamacodechallenge7.R
import com.teamacodechallenge7.TemanDatabase
import com.teamacodechallenge7.data.local.SharedPref

class PilihLawan : AppCompatActivity() {
    private val tag : String = "PilihLawan"
    private lateinit var pilihLawanViewModel: PilihLawanViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilih_lawan)

        val pref = SharedPref
        val mDB: TemanDatabase = TemanDatabase.getInstance(this)!!

        val factory = PilihLawanViewModel.Factory(mDB, pref)
        pilihLawanViewModel = ViewModelProvider(this, factory)[PilihLawanViewModel::class.java]

        recyclerView = findViewById(R.id.recyclerView)
        val ivBack : ImageView = findViewById(R.id.ivBack)
        recyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )
        ivBack.setOnClickListener {
//            startActivity(Intent(this, MainMenuActivity::class.java))
//            finish()
        }

        fetchData()
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    fun fetchData() {
        Log.e(tag,"fetchData")
        pilihLawanViewModel.showList(recyclerView, this@PilihLawan)
    }

    override fun onDestroy() {
        super.onDestroy()
        pilihLawanViewModel.destroyDB()
    }
}