package com.uits.musicplayer.database.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.uits.musicplayer.database.Dao.RecentHistoryDAO
import com.uits.musicplayer.database.entities.RecentHistory


class RecentHistoryRepository(private val recentHistoryDAO: RecentHistoryDAO) {
    val allRecentHistory: LiveData<List<RecentHistory>> = recentHistoryDAO.getRecentHistory()

    suspend fun insert(recentHistory: RecentHistory) {
        recentHistoryDAO.insert(recentHistory)
    }
}