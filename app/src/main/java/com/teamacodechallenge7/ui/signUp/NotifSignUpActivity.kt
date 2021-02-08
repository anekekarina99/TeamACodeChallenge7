package com.teamacodechallenge7.ui.signUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.teamacodechallenge7.R
import com.teamacodechallenge7.ui.loginPage.LoginAct

class NotifSignUpActivity : AppCompatActivity() {
    private val animation by lazy { this.findViewById<LottieAnimationView>(R.id.lav_success) }
    private val btnStart by lazy { this.findViewById<Button>(R.id.btn_start) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notif_sign_up)
        setupAnimation()
        btnStart.setOnClickListener {
            startActivity(Intent(this, LoginAct::class.java))
            finish()
        }
    }

    private fun setupAnimation() {
        animation.speed = 0.5F
        animation.repeatCount = 5
        animation.repeatMode = LottieDrawable.RESTART
        animation.playAnimation()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, LoginAct::class.java))
        finish()
    }
}