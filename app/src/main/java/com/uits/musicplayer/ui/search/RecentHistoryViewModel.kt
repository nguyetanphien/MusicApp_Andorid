package com.uits.musicplayer.ui.search

import android.app.Application
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.uits.musicplayer.database.entities.RecentHistory
import com.uits.musicplayer.database.repository.RecentHistoryRepository
import com.uits.musicplayer.model.AlbumModel
import com.uits.musicplayer.model.HomeModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class RecentHistoryViewModel (context: Application): ViewModel() {
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


}