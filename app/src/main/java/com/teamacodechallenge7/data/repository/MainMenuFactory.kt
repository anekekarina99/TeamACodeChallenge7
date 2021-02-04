package com.teamacodechallenge7.data.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teamacodechallenge7.data.remote.ApiService
import com.teamacodechallenge7.ui.loginPage.LoginViewModel
import com.teamacodechallenge7.ui.mainMenu.MainMenuViewModel

@Suppress("UNCHECKED_CAST")
class MainMenuFactory(private val service: ApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainMenuViewModel(service) as T
    }
}