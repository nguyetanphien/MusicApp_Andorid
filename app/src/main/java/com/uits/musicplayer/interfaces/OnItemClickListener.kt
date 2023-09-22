package com.uits.musicplayer.interfaces

interface OnItemClickListener {
    fun onItemClick(position: Int, id: String)
    fun onItemClick2(position: Int, link: String,title: String,singer: String,images:String)

}