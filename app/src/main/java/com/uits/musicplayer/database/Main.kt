package com.uits.musicplayer.database

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.uits.musicplayer.R
import com.uits.musicplayer.database.entities.RecentHistory
import com.uits.musicplayer.databinding.ActivityMain3Binding

class Main : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        mainViewModel = ViewModelProvider(
            this,
            MainViewModel.RecentViewModelFactory(this.application)
        )[MainViewModel::class.java]

        val recentHistory = RecentHistory()
        recentHistory.id ="1"
        recentHistory.images="https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0"
        recentHistory.name = "1234"
        recentHistory.title = "nhac"
        mainViewModel.insert(recentHistory)

        mainViewModel.allRecentHistorys.observe(this, Observer { list ->
            list.forEach {
                print(it.id)
                print(it.name)
                print(it.title)

            }
        })

        Log.d("pppp", mainViewModel.allRecentHistorys.value.toString())

    }
}