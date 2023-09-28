package com.uits.musicplayer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.uits.musicplayer.database.Dao.FavoriteDAO
import com.uits.musicplayer.database.Dao.RecentHistoryDAO
import com.uits.musicplayer.database.entities.Favorite
import com.uits.musicplayer.database.entities.RecentHistory
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase():RoomDatabase() {
    abstract val mfavoriteDAO: FavoriteDAO

    companion object {
        @Volatile
        private var INSTANCE: FavoriteDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): FavoriteDatabase {

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDatabase::class.java,
                    "Favorite"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class WordDatabaseRecentHistory(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { }
        }
    }
}