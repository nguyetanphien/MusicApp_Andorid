package com.uits.musicplayer.database.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.uits.musicplayer.database.entities.RecentHistory
import com.uits.musicplayer.database.entities.RecentListenings

@Dao
interface RecentListeningDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg recentListenings: RecentListenings?)

    @Update
    fun update(vararg recentListenings: RecentListenings?)

    @Query("DELETE FROM RecentListening")
    fun delete()

    @Query("DELETE FROM RecentListening WHERE ID=:idS ")
    fun deleteId( idS:String)

    @Query("SELECT * FROM RecentListening")
    fun getRecentHistory(): LiveData<List<RecentListenings>>
}