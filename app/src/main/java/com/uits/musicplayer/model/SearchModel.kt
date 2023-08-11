package com.uits.musicplayer.model



 class SearchModel{
  lateinit var title: String
  lateinit var images: String
  lateinit var singer: String
  constructor(title: String, images: String){
   this.title=title
   this.images=images
  }
  constructor(images: String,title: String,singer:String){
   this.images=images
   this.title=title
   this.singer=singer
  }
 }
