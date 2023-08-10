package com.uits.musicplayer.model

class ArtistModel {
     lateinit var  time:String
    lateinit var  image: String
    lateinit var  nameAlbum:String
    lateinit var  yearAlbum:String
    lateinit var nameSinger:String

    constructor( image: String, nameAlbum:String,  yearAlbum:String)  {
        this.image=image
        this.nameAlbum=nameAlbum
        this.yearAlbum=yearAlbum
    }

    constructor(image: String, nameAlbum:String, yearAlbum:String, time:String) {
        this.image=image
        this.nameAlbum=nameAlbum
        this.yearAlbum=yearAlbum
        this.time=time
    }
    constructor(image: String, nameSong:String, nameSinger:String, yearAlbum:String, time:String) {
        this.image=image
        this.nameAlbum=nameSong
        this.nameSinger=nameSinger
        this.yearAlbum=yearAlbum
        this.time=time
    }


}
