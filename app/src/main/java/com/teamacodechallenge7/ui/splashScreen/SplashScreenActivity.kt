package com.teamacodechallenge7.ui.splashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.teamacodechallenge7.R
import com.teamacodechallenge7.data.local.SharedPref
import com.teamacodechallenge7.ui.landingPage.LandingPageActivity

class SplashScreenActivity : AppCompatActivity(), SplashScreenNavigator {
    private lateinit var splashScreenViewModel: SplashScreenViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val ivSplashScreen1 by lazy {this.findViewById<ImageView>(R.id.ivSplashScreen1)}
        splashScreenViewModel = ViewModelProvider(this, defaultViewModelProviderFactory)[SplashScreenViewModel::class.java]
        splashScreenViewModel.navigator = this
        Glide.with(this)
            .load("https://i.ibb.co/HC5ZPgD/splash-screen1.png")
            .into(ivSplashScreen1)
        splashScreenViewModel.checkIsLogin()
    }

    override fun onLogged() {
        val handler = Handler()
        handler.postDelayed({
            startActivity(Intent(this, LandingPageActivity::class.java))
            finish() }, 3000)
    }

    override fun unLogged() {
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this, LandingPageActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}