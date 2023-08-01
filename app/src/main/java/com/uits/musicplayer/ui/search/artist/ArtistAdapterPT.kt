package com.uits.musicplayer.ui.search.artist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uits.musicplayer.R
import com.uits.musicplayer.interfaces.OnItemClickListener
import com.uits.musicplayer.model.ArtistModel

class ArtistAdapterPT(
    var context: ArtistActivity,
    var list: MutableList<ArtistModel>,
    var onClick: OnItemClickListener
) : RecyclerView.Adapter<ArtistAdapterPT.ArtistPTViewHodel>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistPTViewHodel {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.custom_rv_popular_tracks,parent,false)
        return ArtistPTViewHodel(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ArtistPTViewHodel, position: Int) {
        Glide.with(context)
            .load(list[position].image)
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher)
            .into(holder.img)
        holder.nameAlbumPT.text=list[position].nameAlbum
        holder.time.text=list[position].time
    }
    class ArtistPTViewHodel(itemView:View) :RecyclerView.ViewHolder(itemView) {
        var img:ImageView=itemView.findViewById(R.id.imgPopularTrack)
        var nameAlbumPT:TextView=itemView.findViewById(R.id.txtNamePT)
        var time:TextView=itemView.findViewById(R.id.txtTime)
    }
}