package com.uits.musicplayer.ui.search

import android.content.Intent
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
import com.uits.musicplayer.model.SearchModel
import com.uits.musicplayer.ui.search.artist.ArtistActivity


class AdapterMusic(
    val context: FragmentActivity?,
    val mListData: MutableList<SearchModel>,
    val onClick: OnItemClickListener
) : RecyclerView.Adapter<AdapterMusic.MusicViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_music, parent, false)
        return MusicViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        holder.title.text = mListData[position].title
        if (context != null) {
            Glide.with(context)
                .load(mListData[position].images)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.img)
        };

        holder.itemView.setOnClickListener {
            val _context=holder.img.context
            val intent = Intent(_context, ArtistActivity::class.java)
            _context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mListData.size
    }

    class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.action_image)
        val title: TextView = itemView.findViewById(R.id.title)
    }
}
