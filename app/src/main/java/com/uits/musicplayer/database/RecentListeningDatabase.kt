package com.uits.musicplayer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.uits.musicplayer.database.Dao.RecentHistoryDAO
import com.uits.musicplayer.database.Dao.RecentListeningDAO
import com.uits.musicplayer.database.entities.RecentHistory
import com.uits.musicplayer.database.entities.RecentListenings
import kotlinx.coroutines.CoroutineScope
@Database(entities = [RecentListenings::class], version = 2, exportSchema = false)
@TypeConverters(DateTypeConverter::class)
abstract class RecentListeningDatabase:RoomDatabase() {
    abstract val mRecentListeningDAO: RecentListeningDAO

    companion object {
        @Volatile
        private var INSTANCE: RecentListeningDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): RecentListeningDatabase {

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecentListeningDatabase::class.java,
                    "RecentListening"
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