package com.uits.musicplayer.ui.player.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uits.musicplayer.R
import com.uits.musicplayer.interfaces.OnItemClickListener
import com.uits.musicplayer.model.HomeModel

class HomeAdapterTopAlbum(
    val context: FragmentActivity?,
    val mListTA: MutableList<HomeModel>,
    val onClick: OnItemClickListener
) : RecyclerView.Adapter<HomeAdapterTopAlbum.HomeTAViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTAViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.custom_rv_album,parent,false)
        return HomeTAViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mListTA.size
    }

    override fun onBindViewHolder(holder: HomeTAViewHolder, position: Int) {
        if (context != null) {
            Glide.with(context)
                .load(mListTA[position].image)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.img)
        }

        holder.txtNameAlbum.text=mListTA[position].nameAlbum
        holder.txtYearAlbum.text=mListTA[position].yearAlbum
    }
    class HomeTAViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.imgAlbum)
        val txtNameAlbum: TextView =itemView.findViewById(R.id.txtNameAlbum)
        val txtYearAlbum: TextView=itemView.findViewById(R.id.txtYearAlbum)
    }
}