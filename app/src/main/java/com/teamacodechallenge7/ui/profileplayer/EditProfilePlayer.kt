package com.teamacodechallenge7.ui.profileplayer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.teamacodechallenge7.R
import com.teamacodechallenge7.data.local.SharedPref
import com.teamacodechallenge7.data.remote.ApiModule
import com.teamacodechallenge7.ui.loginPage.LoginAct
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File

class EditProfilePlayer : AppCompatActivity() {
    private val tag: String = "EditProfilePlayer"
    private lateinit var editProfilePlayerViewModel: EditProfilePlayerViewModel
    private lateinit var ivProfile: ImageView
    private lateinit var btSave: Button
    private lateinit var cropImageView: CircleImageView
    private lateinit var lParent: LinearLayout
    private var filePath: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_player)

        val pref = SharedPref
        val factory = EditProfilePlayerViewModel.Factory(ApiModule.service, pref)
        editProfilePlayerViewModel =
            ViewModelProvider(this, factory)[EditProfilePlayerViewModel::class.java]

        cropImageView = findViewById(R.id.cropImageView)
        lParent = findViewById(R.id.lParent)
        btSave = findViewById(R.id.btSave)
        val btClose = findViewById<ImageView>(R.id.btClose)
        ivProfile = findViewById(R.id.ivProfile)
        val rlProfile = findViewById<RelativeLayout>(R.id.rlProfile)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etEmail = findViewById<EditText>(R.id.etEmail)

        fetchData()

        btClose.setOnClickListener {
            val intent = Intent(this, ProfilePlayer::class.java)
            startActivity(intent)
            finish()
        }
        btSave.setOnClickListener {
            val newUsername = etUsername.text.toString()
            val newEmail = etEmail.text.toString()
            if (filePath == null) {
                Toast.makeText(this, "Pilih gambar dulu", Toast.LENGTH_SHORT).show()
            } else {
                filePath?.let { it1 ->
                    editProfilePlayerViewModel.upload(
                        newUsername, newEmail, it1
                    )
                }
                btSave.isEnabled = false
                btSave.text = resources.getText(R.string.loading)
            }
        }

        rlProfile.setOnClickListener {
            pickImage()
        }

        editProfilePlayerViewModel.resultMessage.observe(this) {
            Log.e(tag, it.toString())
            if (it == "Token is expired" || it == "Invalid Token") {
                val snackbar = Snackbar.make(
                    lParent,
                    "Waktu bermain sudah selesai, main lagi? silahkan Login",
                    Snackbar.LENGTH_INDEFINITE
                )
                snackbar.setAction("Login") {
                    snackbar.dismiss()
                    startActivity(Intent(this, LoginAct::class.java))
                    finish()
                }.show()
            }
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            Log.e(tag, it.toString())
        }
        editProfilePlayerViewModel.resultUser.observe(this) {
            etUsername.setText(it.data.username)
            etEmail.setText(it.data.email)
            Glide
                .with(this)
                .load(it.data.photo)
                .centerCrop()
                .circleCrop()
                .placeholder(R.drawable.ic_user)
                .into(ivProfile)
        }
        editProfilePlayerViewModel.resultPost.observe(this) {
            if (it) {
                val intent = Intent(this, ProfilePlayer::class.java)
                startActivity(intent)
                finish()
            } else {
                Handler(Looper.getMainLooper()).postDelayed({
                    btSave.isEnabled = true
                    btSave.text = resources.getText(R.string.save)
                }, 200L)
            }
        }
    }

    private fun pickImage() {
        CropImage.activity()
            .setGuidelines(CropImageView.Guidelines.ON)
            .setAspectRatio(10, 10)
            .setScaleType(CropImageView.ScaleType.CENTER_CROP)
            .start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri: Uri = result.uri
                filePath = resultUri.toFile()
                Log.e(tag, resultUri.toString())
                cropImageView.setImageURI(resultUri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                Log.e(tag, "Image error$error")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    private fun fetchData() {
        editProfilePlayerViewModel.playerData()
    }

    override fun onBackPressed() {
        startActivity(Intent(this, ProfilePlayer::class.java))
        finish()
    }
}