package com.uits.musicplayer.database.repository

import android.app.Application
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import com.uits.musicplayer.database.AppDatabase
import com.uits.musicplayer.database.Dao.RecentHistoryDAO
import com.uits.musicplayer.database.entities.RecentHistory



class RecentHistoryRepository (application: FragmentActivity) {
    private val recentHistoryDAO: RecentHistoryDAO
    //    val allRecentHistory: LiveData<List<RecentHistory>> = recentHistoryDAO.getRecentHistory()
    init {
        val rencentDatabase: AppDatabase=AppDatabase.getDatabase(application)
        recentHistoryDAO= rencentDatabase.mRecentHistoryDao
    }

    suspend fun insert(recentHistory:RecentHistory) {
        recentHistoryDAO.insert(recentHistory)
    }
    suspend fun deleteid(id : String) {
        recentHistoryDAO.deleteid(id)
    }suspend fun delete() {
        recentHistoryDAO.delete()
    }
    fun get():
            LiveData<List<RecentHistory>> = recentHistoryDAO.getRecentHistory()

}
