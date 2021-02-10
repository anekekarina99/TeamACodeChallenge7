package com.teamacodechallenge7.ui.about

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.VideoView
import com.teamacodechallenge7.R
import com.teamacodechallenge7.ui.mainMenu.MainMenuAct

class InstructionActivity : AppCompatActivity() {

    private val videoView by lazy { this.findViewById<VideoView>(R.id.video_instruksi) }
    private val ivBack by lazy { this.findViewById<ImageView>(R.id.iv_back) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instruction)
        val path = "android.resource://$packageName/${R.raw.video}"
        videoView.setVideoURI(Uri.parse(path))
        videoView.start()

        ivBack.setOnClickListener {
            startActivity(Intent(this, MainMenuAct::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainMenuAct::class.java))
        finish()
    }
}