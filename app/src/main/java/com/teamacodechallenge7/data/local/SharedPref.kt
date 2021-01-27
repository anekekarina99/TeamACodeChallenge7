package com.teamacodechallenge7.data.local

import android.content.Context
import com.teamacodechallenge7.utils.App

object SharedPref {
    private const val KEY_ISLOGIN = "KEY_ISLOGIN"
    private const val KEY_ID = "KEY_ID"
    private const val KEY_USERNAME = "KEY_USERNAME"

    private val pref = App.context.get()?.getSharedPreferences("CodeChallenge7", Context.MODE_PRIVATE)

    var isLogin: Boolean?
        get() = pref?.getBoolean(KEY_ISLOGIN, false)
        set(value) {
            value?.let{
                pref?.edit()
                    ?.putBoolean(KEY_ISLOGIN, it)
                    ?.apply()
            }
        }
    var id: Int?
        get()  = pref?.getInt(KEY_ID, 0)
        set(value) {
            value?.let {
                pref?.edit()
                    ?.putInt(KEY_ID, it)
                    ?.apply()
            }
        }

    var username: String?
        get() = pref?.getString(KEY_USERNAME, "")
        set(value) {
            value?.let {
                pref?.edit()
                    ?.putString(KEY_USERNAME, it)
                    ?.apply()
            }
        }
}