package com.sara.giphygif

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GifyApplication: Application() {


    override fun onCreate() {
        super.onCreate()
    }

}