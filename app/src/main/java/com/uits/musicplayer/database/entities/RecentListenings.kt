package com.uits.musicplayer.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RecentListening")
class RecentListenings {
    @PrimaryKey
    @ColumnInfo(name = "ID")
    var id: String = ""

    @ColumnInfo(name = "Images")
    var images: String = ""

    @ColumnInfo(name = "Title")
    var title: String = ""

    @ColumnInfo(name = "Time")
    var time: String = ""

    @ColumnInfo(name = "Link")
    var link: String = ""
}