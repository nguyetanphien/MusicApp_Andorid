package com.uits.musicplayer.database.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.uits.musicplayer.database.entities.RecentHistory

@Dao
public interface RecentHistoryDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg recentHistory: RecentHistory?)

    @Update
    fun update(vararg recentHistory: RecentHistory?)

    @Delete
    fun delete(vararg recentHistory: RecentHistory)

    @Query("SELECT * FROM RecentMusic")
    fun getRecentHistory(): LiveData<List<RecentHistory>>


}