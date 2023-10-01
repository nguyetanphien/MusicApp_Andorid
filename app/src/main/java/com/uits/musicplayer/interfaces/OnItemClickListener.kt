package com.uits.musicplayer.interfaces

import android.widget.ImageButton

interface OnItemClickListener {
    fun onItemClick(position: Int, id: String, button: ImageButton,link: String,title: String,singer: String,images:String)
    fun onItemClick2(position: Int,id: String, link: String,title: String,singer: String,images:String)

}