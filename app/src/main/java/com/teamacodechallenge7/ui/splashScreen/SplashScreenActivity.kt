package com.teamacodechallenge7.ui.splashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.teamacodechallenge7.R
import com.teamacodechallenge7.data.local.SharedPref
import com.teamacodechallenge7.ui.landingPage.LandingPageActivity
import com.teamacodechallenge7.ui.mainMenu.MainMenuAct
import com.teamacodechallenge7.utils.refreshToken

class SplashScreenActivity : AppCompatActivity(), SplashScreenNavigator {
    private lateinit var splashScreenViewModel: SplashScreenViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val ivSplashScreen1 by lazy {this.findViewById<ImageView>(R.id.ivSplashScreen1)}
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.anim_splash_screen)
        ivSplashScreen1.startAnimation(animation)
        splashScreenViewModel = ViewModelProvider(this, defaultViewModelProviderFactory)[SplashScreenViewModel::class.java]
        splashScreenViewModel.navigator = this
        splashScreenViewModel.checkIsLogin()
    }

    override fun onLogged() {
        val handler = Handler()
        handler.postDelayed({

            startActivity(Intent(this, MainMenuAct::class.java))
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