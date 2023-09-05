package com.uits.musicplayer.ui.search

import android.app.Application
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uits.musicplayer.database.entities.RecentHistory
import com.uits.musicplayer.database.repository.RecentHistoryRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class RecentHistoryViewModel (context: FragmentActivity): ViewModel() {
    private val recentHistoryRespository: RecentHistoryRepository = RecentHistoryRepository(context)
    fun insert(recentHistory: RecentHistory) =
        MainScope().launch(Dispatchers.IO) {
            recentHistoryRespository.insert(recentHistory)
        }
    fun deleteid(id :String) =
        MainScope().launch(Dispatchers.IO) {
            recentHistoryRespository.deleteid(id)
        }
    fun delete() =
        MainScope().launch(Dispatchers.IO) {
            recentHistoryRespository.delete()
        }
     fun getDAta() :
        LiveData<List<RecentHistory>> =recentHistoryRespository.get()

    class RecentViewModelFactory(private val application: FragmentActivity): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RecentHistoryViewModel::class.java))
                return RecentHistoryViewModel(application) as T
            throw IllegalArgumentException("Error view model")
        }
    }
}