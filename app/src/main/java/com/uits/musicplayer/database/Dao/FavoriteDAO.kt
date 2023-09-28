package com.uits.musicplayer.database.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.uits.musicplayer.database.entities.Favorite


@Dao
interface FavoriteDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg favorite: Favorite?)

    @Update
    fun update(vararg favorite: Favorite?)

    @Query("DELETE FROM Favorite")
    fun delete()

//    @Query("DELETE FROM Favorite WHERE ID=:idS ")
//    fun deleteId( idS:String)

    @Query("SELECT * FROM Favorite")
    fun getFavorite(): LiveData<List<Favorite>>
}