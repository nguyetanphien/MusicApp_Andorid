package com.uits.musicplayer.database.repository

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import com.uits.musicplayer.database.AppDatabase
import com.uits.musicplayer.database.Dao.FavoriteDAO
import com.uits.musicplayer.database.Dao.RecentHistoryDAO
import com.uits.musicplayer.database.FavoriteDatabase
import com.uits.musicplayer.database.entities.Favorite
import com.uits.musicplayer.database.entities.RecentHistory

class FavoriteRepository(application: Context) {
    private val favoriteDao: FavoriteDAO

    init {
        val favoriteDatabase: FavoriteDatabase = FavoriteDatabase.getDatabase(application)
        favoriteDao = favoriteDatabase.mfavoriteDAO
    }

    suspend fun insert(favorite: Favorite) {
        favoriteDao.insert(favorite)
    }

     fun deleteId(id: String) {
        favoriteDao.deleteId(id)
    }

    suspend fun delete() {
        favoriteDao.delete()
    }

    fun get():
            LiveData<List<Favorite>> = favoriteDao.getFavorite()
}