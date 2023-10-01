package com.uits.musicplayer.ui.favorite.Track


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
import com.uits.musicplayer.database.entities.Favorite
import com.uits.musicplayer.interfaces.OnItemClickListener
import com.uits.musicplayer.model.AlbumModel
import com.uits.musicplayer.model.ArtistModel
import com.uits.musicplayer.ui.player.PlayerActivity

class TrackAdapter(
    var context: FragmentActivity,
    var mList: MutableList<Favorite>,
    var onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<TrackAdapter.TrackViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_favorite_track, parent, false)
        return TrackViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        if (context != null) {
            Glide.with(context)
                .load(mList[position].images)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.img)
        }
        holder.nameSong.text = mList[position].title
        holder.nameSinger.text = mList[position].singer
        holder.time.text = mList[position].time
        holder.itemView.setOnClickListener(View.OnClickListener {
            onItemClickListener.onItemClick2(
                position,
                mList[position].id,
                mList[position].link,
                mList[position].title,
                mList[position].singer,
                mList[position].images
            )
        })
        holder.ibtnChosseFTrack.setOnClickListener(View.OnClickListener {
            onItemClickListener.onItemClick(
                position, mList[position].id, holder.ibtnChosseFTrack,
                mList[position].link,
                mList[position].title,
                mList[position].singer,
                mList[position].images
            )
        })
    }


    class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.imgFavoriteTrack)
        val ibtnChosseFTrack: ImageButton = itemView.findViewById(R.id.ibtnChosseFTrack)
        val nameSong: TextView = itemView.findViewById(R.id.txtNameFTrack)
        val nameSinger: TextView = itemView.findViewById(R.id.txtNameSingerFTrack)
        val time: TextView = itemView.findViewById(R.id.txtTimeFTrack)


    }
}