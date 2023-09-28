package com.uits.musicplayer.database.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favorite")
class Favorite() :Parcelable {
    @PrimaryKey
    @ColumnInfo(name = "ID")
    var id: String = ""
    @ColumnInfo(name = "Images")
    var images: String = ""

    @ColumnInfo(name = "Title")
    var title: String = ""

    @ColumnInfo(name = "Singer")
    var singer: String = ""

    @ColumnInfo(name = "Time")
    var time: String = ""

    @ColumnInfo(name = "Link")
    var link: String = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readString().toString()
        images = parcel.readString().toString()
        title = parcel.readString().toString()
        singer = parcel.readString().toString()
        time = parcel.readString().toString()
        link = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(images)
        parcel.writeString(title)
        parcel.writeString(singer)
        parcel.writeString(time)
        parcel.writeString(link)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Favorite> {
        override fun createFromParcel(parcel: Parcel): Favorite {
            return Favorite(parcel)
        }

        override fun newArray(size: Int): Array<Favorite?> {
            return arrayOfNulls(size)
        }
    }
}