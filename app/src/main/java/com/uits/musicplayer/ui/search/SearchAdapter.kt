package com.uits.musicplayer.ui.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uits.musicplayer.R
import com.uits.musicplayer.database.entities.RecentHistory
import com.uits.musicplayer.interfaces.OnItemClickListener
import com.uits.musicplayer.model.AlbumModel
import com.uits.musicplayer.ui.player.PlayerActivity

class SearchAdapter(
    var context: FragmentActivity?,
    var mList: MutableList<AlbumModel>,
    val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<SearchAdapter.SearchAdapterViewHolder>() {
    lateinit var id: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.custom_rv_search, parent, false)
        return SearchAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: SearchAdapterViewHolder, position: Int) {

        Glide.with(context!!)
            .load(mList[position].images)
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher)
            .into(holder.imgAvataRecentS)
        holder.txtNameSongRecentS.text = mList[position].nameSong
        holder.txtNameSingerRecentS.text = mList[position].nameSinger
        holder.itemView.setOnClickListener(View.OnClickListener {
            onItemClickListener.onItemClick2(
                position,
                mList[position].id,
                mList[position].link,
                mList[position].nameSong,
                mList[position].nameSinger,
                mList[position].images
            )
        })
    }

    class SearchAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgAvataRecentS: ImageView = itemView.findViewById(R.id.imgAvataRecentS)
        val txtNameSongRecentS: TextView = itemView.findViewById(R.id.txtNameSongRecentS)
        val txtNameSingerRecentS: TextView = itemView.findViewById(R.id.txtNameSingerRecentS)



    }
}