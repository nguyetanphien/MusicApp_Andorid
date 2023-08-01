package com.uits.musicplayer.model

class HomeModel {
    lateinit var  time:String
    lateinit var  image: String
    lateinit var  nameAlbum:String
    lateinit var  yearAlbum:String

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
}
