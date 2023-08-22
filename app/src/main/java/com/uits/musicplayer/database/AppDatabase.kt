package com.uits.musicplayer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.uits.musicplayer.database.Dao.RecentHistoryDAO
import com.uits.musicplayer.database.entities.RecentHistory
import kotlinx.coroutines.CoroutineScope

@Database(entities = [RecentHistory::class], version = 1, exportSchema = false)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase() : RoomDatabase() {
    abstract val mRecentHistoryDao: RecentHistoryDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        @Synchronized
        fun getDatabase(context: Context): AppDatabase {

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "RecentMusic"
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