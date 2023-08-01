package com.uits.musicplayer.ui.search.artist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uits.musicplayer.R
import com.uits.musicplayer.model.ArtistModel

class ArtistAdapter(
    val context: ArtistActivity,
    val list: MutableList<ArtistModel>,
    val onClick: com.uits.musicplayer.interfaces.OnItemClickListener
) : RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {
//    val context: FragmentActivity? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.custom_rv_album, parent, false)
        return ArtistViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {

        if (context != null) {
            Glide.with(context)
                .load(list[position].image)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.img)
        }

        holder.txtNameAlbum.text = list[position].nameAlbum
        holder.txtYearAlbum.text = list[position].yearAlbum


    }

    class ArtistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.imgAlbum)
        val txtNameAlbum: TextView = itemView.findViewById(R.id.txtNameAlbum)
        val txtYearAlbum: TextView = itemView.findViewById(R.id.txtYearAlbum)
    }
}