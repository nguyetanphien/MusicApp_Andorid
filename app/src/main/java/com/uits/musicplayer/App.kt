package com.uits.musicplayer

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.uits.musicplayer.service.APIClient

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        APIClient().init()
        FirebaseApp.initializeApp(this)
        Firebase.initialize(this)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}