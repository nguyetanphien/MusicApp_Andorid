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

class HomeAdapterRL(
    val context: FragmentActivity?,
    val mListRL: MutableList<HomeModel>,
    val onClick: OnItemClickListener
) : RecyclerView.Adapter<HomeAdapterRL.HomeRLAdapter>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRLAdapter {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_rv_popular_tracks, parent, false)
        return HomeRLAdapter(view)
    }

    override fun getItemCount(): Int {
        return mListRL.size
    }

    override fun onBindViewHolder(holder: HomeRLAdapter, position: Int) {
        if (context != null) {
            Glide.with(context)
                .load(mListRL[position].image)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imgRL)
        }
        holder.txtName.text = mListRL[position].nameAlbum
        holder.txtTime.text = mListRL[position].time
    }

    class HomeRLAdapter(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val imgRL: ImageView = itemview.findViewById(R.id.imgPopularTrack)
        val txtName: TextView = itemview.findViewById(R.id.txtNamePT)
        val txtTime: TextView = itemview.findViewById(R.id.txtTime)
    }

}