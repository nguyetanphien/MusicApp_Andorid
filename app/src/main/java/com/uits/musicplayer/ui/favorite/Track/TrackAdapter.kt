package com.uits.musicplayer.ui.favorite.Track


import android.content.Intent
import android.provider.MediaStore.Audio.Artists
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
import com.uits.musicplayer.interfaces.OnItemClickListener
import com.uits.musicplayer.model.ArtistModel
import com.uits.musicplayer.ui.player.PlayerActivity

class TrackAdapter(
    var context: FragmentActivity,
    var mList: MutableList<ArtistModel>,
    var onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<TrackAdapter.TrackViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.custom_favorite_track,parent,false)
        return TrackViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        if (context != null) {
            Glide.with(context)
                .load(mList[position].image)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.img)
        }
        holder.nameSong.text=mList[position].nameAlbum
        holder.nameSinger.text=mList[position].nameSinger
        holder.time.text=mList[position].time
        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, PlayerActivity::class.java)
            context.startActivity(intent)
        })
    }

    class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img:ImageView=itemView.findViewById(R.id.imgFavoriteTrack)
        val nameSong:TextView=itemView.findViewById(R.id.txtNameFTrack)
        val nameSinger:TextView=itemView.findViewById(R.id.txtNameSingerFTrack)
        val time:TextView=itemView.findViewById(R.id.txtTimeFTrack)


    }
}