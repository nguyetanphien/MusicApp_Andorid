package com.uits.musicplayer

import android.app.Application
import com.uits.musicplayer.service.APIClient

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        APIClient().init()
    }
}