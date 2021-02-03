package com.teamacodechallenge7.ui.profileplayer

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.teamacodechallenge7.R
import com.teamacodechallenge7.data.local.SharedPref
import com.teamacodechallenge7.data.remote.ApiModule
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.File

class EditProfilePlayer : AppCompatActivity() {
    private val tag : String = "EditProfilePlayer"
    private lateinit var editProfilePlayerViewModel: EditProfilePlayerViewModel
    private lateinit var ivProfile : ImageView
    private val STORAGE_AND_CAMERA_REQUEST_CODE = 100
    private var  storageAndCameraPermission =  arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )
    private var filePath: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_player)

        val pref = SharedPref
        val factory = EditProfilePlayerViewModel.Factory(ApiModule.service, pref)
        editProfilePlayerViewModel = ViewModelProvider(this, factory)[EditProfilePlayerViewModel::class.java]

        val btSave = findViewById<Button>(R.id.btSave)
        val btClose = findViewById<ImageView>(R.id.btClose)
        ivProfile = findViewById(R.id.ivProfile)
        val rlProfile = findViewById<RelativeLayout>(R.id.rlProfile)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        fetchData()

        btClose.setOnClickListener {
            val intent = Intent(this, ProfilePlayer::class.java)
            startActivity(intent)
        }
        btSave.setOnClickListener {
            filePath?.let { it1 -> editProfilePlayerViewModel.upload("Username123", "emailku123@gmail.com", it1) }
        }

        rlProfile.setOnClickListener {
            if (!checkStorageAndCameraPermission()) {
                requestStorageAndCameraPermission()
            } else{
                pickImage()
            }
        }

        editProfilePlayerViewModel.resultUsers.observe(this) {
            Log.e(tag, it.toString())
            etUsername.hint = it.data.username
            etEmail.hint = it.data.email
            Glide
                .with(this)
                .load(it.data.photo)
                .centerCrop()
                .circleCrop()
                .placeholder(R.drawable.ic_people)
                .into(ivProfile);
        }
        editProfilePlayerViewModel.resultPost.observe(this) {
            Glide.with(this)
                .load(it.data.photo)
                .into(ivProfile)
        }

    }

    private fun pickImage() {
        CropImage.activity()
            .setGuidelines(CropImageView.Guidelines.ON)
            .start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri: Uri = result.uri
                filePath = resultUri.toFile()
                Glide
                    .with(this)
                    .load(filePath)
                    .centerCrop()
                    .circleCrop()
                    .placeholder(R.drawable.ic_people)
                    .into(ivProfile);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }

    private fun checkStorageAndCameraPermission(): Boolean {
        val result =
            (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED)
        val result1 = (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED)
        return result && result1
    }

    private fun requestStorageAndCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            storageAndCameraPermission, STORAGE_AND_CAMERA_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_AND_CAMERA_REQUEST_CODE -> {
                if (grantResults.size > 0) {
                    val readStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    if (readStorageAccepted && cameraAccepted) {
                        pickImage()
                    } else {
                        Toast.makeText(this, "Permissions are required!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    fun fetchData(){
//        editProfilePlayerViewModel.playerData()
    }
}