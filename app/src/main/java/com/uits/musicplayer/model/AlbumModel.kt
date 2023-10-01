package com.uits.musicplayer.model

import android.os.Parcel
import android.os.Parcelable
import java.lang.reflect.Parameter

class AlbumModel(
    var id:String,
   var nameSong: String,
   var  nameSinger: String,
   var  link: String,
   var time: String,
   var images: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nameSong)
        parcel.writeString(nameSinger)
        parcel.writeString(link)
        parcel.writeString(time)
        parcel.writeString(images)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AlbumModel> {
        override fun createFromParcel(parcel: Parcel): AlbumModel {
            return AlbumModel(parcel)
        }

        override fun newArray(size: Int): Array<AlbumModel?> {
            return arrayOfNulls(size)
        }
    }
}

