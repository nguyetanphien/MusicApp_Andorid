package com.uits.musicplayer.database

import android.app.Application
import android.content.Context
import android.widget.Adapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.uits.musicplayer.database.entities.RecentHistory


import com.uits.musicplayer.database.repository.RecentHistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException


class MainViewModel(context: Application) : AndroidViewModel(context) {
    private val recentHistoryRespository: RecentHistoryRepository
    val allRecentHistorys: LiveData<List<RecentHistory>>

    init {
        val recentHistoryDAO = AppDatabase.getDatabase(context).mRecentHistoryDao
        recentHistoryRespository = RecentHistoryRepository(recentHistoryDAO)
        allRecentHistorys = recentHistoryRespository.allRecentHistory


    }


    fun insert(recentHistory: RecentHistory) =
        viewModelScope.launch(Dispatchers.IO) {
            recentHistoryRespository.insert(recentHistory)
        }
    class RecentViewModelFactory(private val application: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java))
                return MainViewModel(application) as T
            throw IllegalArgumentException("Error view model")
        }
    }

}