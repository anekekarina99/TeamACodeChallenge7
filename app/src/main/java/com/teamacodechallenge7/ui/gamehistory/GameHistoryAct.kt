package com.teamacodechallenge7.ui.gamehistory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamacodechallenge7.data.remote.ApiModule
import com.teamacodechallenge7.databinding.ActivityGameHistoryBinding
import com.teamacodechallenge7.ui.mainMenu.MainMenuAct

private var rvMain: RecyclerView? = null

class GameHistoryAct : AppCompatActivity() {
    private lateinit var binding: ActivityGameHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = GameHistoryViewModel.Factory(ApiModule.service)

        val viewModel = ViewModelProvider(this, factory)[GameHistoryViewModel::class.java]
        viewModel.listScore()

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, MainMenuAct::class.java))
            finish()
        }

        rvMain = binding.recyclerView
        viewModel.resultScore.observe(this) {
            val adapter = GameHistoryAdapter(it, this)
            rvMain?.layoutManager = LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false
            )
            rvMain?.adapter = adapter
        }
    }
}