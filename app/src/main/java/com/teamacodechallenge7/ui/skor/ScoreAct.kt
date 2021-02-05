package com.teamacodechallenge7.ui.skor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamacodechallenge7.R
import com.teamacodechallenge7.data.remote.ApiModule

class ScoreAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         var rvMain: RecyclerView? = null
        setContentView(R.layout.activity_score)
        val factory = ScoreViewModel.Factory(ApiModule.service)
        rvMain = findViewById(R.id.recyclerView)
        val viewModel = ViewModelProvider(this, factory)[ScoreViewModel::class.java]
        viewModel.listScore()
        viewModel.resultScore.observe(this) { datae->
            Toast.makeText(this,datae.toString(),Toast.LENGTH_SHORT).show()
            val adapter = ScoreAdapter(viewModel,datae,this)
            rvMain.layoutManager = LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false
            )
            rvMain.adapter=adapter
        }
    }
}