package com.uits.musicplayer.database.repository

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import com.uits.musicplayer.database.AppDatabase
import com.uits.musicplayer.database.Dao.RecentHistoryDAO
import com.uits.musicplayer.database.Dao.RecentListeningDAO
import com.uits.musicplayer.database.RecentListeningDatabase
import com.uits.musicplayer.database.entities.RecentHistory
import com.uits.musicplayer.database.entities.RecentListenings

class RecentListeningRepository (application: Context) {
    private val recentListeningsDAO: RecentListeningDAO


    init {
        val rencentDatabase: RecentListeningDatabase = RecentListeningDatabase.getDatabase(application)
        recentListeningsDAO= rencentDatabase.mRecentListeningDAO
    }

    suspend fun insert(recentListenings: RecentListenings) {
        recentListeningsDAO.insert(recentListenings)
    }
    suspend fun deleteid(id : String) {
        recentListeningsDAO.deleteId(id)
    }suspend fun delete() {
        recentListeningsDAO.delete()
    }
    fun get():
            LiveData<List<RecentListenings>> = recentListeningsDAO.getRecentHistory()

}