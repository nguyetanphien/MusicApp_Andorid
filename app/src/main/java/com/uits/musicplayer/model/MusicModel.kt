package com.uits.musicplayer.model

import com.google.gson.annotations.SerializedName

class MusicModel {
    @SerializedName("id")
    lateinit var id: String
    @SerializedName("title")
    lateinit var title: String
    @SerializedName("album")
    lateinit var album: String
    @SerializedName("artist")
    lateinit var artist: String
    @SerializedName("genre")
    lateinit var genre: String
    @SerializedName("source")
    lateinit var source: String
    @SerializedName("image")
    lateinit var image: String
    @SerializedName("trackNumber")
    var trackNumber: Int? = 0
    @SerializedName("totalTrackCount")
    var totalTrackCount: Int? = 0
    @SerializedName("duration")
    var duration: Int? = 0
    @SerializedName("site")
    lateinit var site: String

}