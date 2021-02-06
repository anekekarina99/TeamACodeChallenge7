package com.teamacodechallenge7.utils

import android.app.Application
import android.content.Context
import java.lang.ref.WeakReference

class App: Application() {
    companion object{
        lateinit  var context : WeakReference<Context>
    }

    override fun onCreate() {
        super.onCreate()
        context = WeakReference(applicationContext)

    }
}