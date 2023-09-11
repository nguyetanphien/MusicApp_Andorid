package com.uits.musicplayer.ui.search

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

class RecentAdapter(
    var context: FragmentActivity?,
    var mList: MutableList<RecentHistory>,
    val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecentAdapter.RecentViewHolder>() {
    lateinit var id: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.custom_recent, parent, false)
        return RecentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {

        Glide.with(context!!)
            .load(mList[position].images)
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher)
            .into(holder.imgAvataRecent)
        holder.txtNameSongRecent.text=mList[position].title
        holder.txtNameSingerRecent.text=mList[position].name
        holder.ibtnDelete.setOnClickListener(View.OnClickListener {
            id=mList[position].id
            onItemClickListener.onItemClick(position,id)
        })

    }

    class RecentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgAvataRecent: ImageView = itemView.findViewById(R.id.imgAvataRecent)
        val txtNameSongRecent: TextView = itemView.findViewById(R.id.txtNameSongRecent)
        val txtNameSingerRecent: TextView = itemView.findViewById(R.id.txtNameSingerRecent)
        val ibtnDelete: ImageButton = itemView.findViewById(R.id.ibtnRemoveRecent)


    }
}