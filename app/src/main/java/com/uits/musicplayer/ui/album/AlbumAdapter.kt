package com.uits.musicplayer.ui.album

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.uits.musicplayer.R
import com.uits.musicplayer.interfaces.OnItemClickListener
import com.uits.musicplayer.model.AlbumModel
import com.uits.musicplayer.ui.player.PlayerActivity


class AlbumAdapter(
    var context: FragmentActivity?,
    var mutableList: MutableList<AlbumModel>,
    var onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.custom_list_as, parent, false)
        return AlbumViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mutableList.size
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val p = position + 1
        holder.txtPosition.text = p.toString()
        holder.nameSong.text = mutableList[position].nameSong
        holder.txtNameSinger.text = mutableList[position].nameSinger
        holder.txtTimeAS.text = mutableList[position].time
        holder.itemView.setOnClickListener(View.OnClickListener {
            onItemClickListener.onItemClick2(
                position,
                mutableList[position].link,
                mutableList[position].nameSong,
                mutableList[position].nameSinger,
                mutableList[position].images

            )
//            val intent = Intent(context, PlayerActivity::class.java)
//            intent.putExtra("music", mutableList[position].link)
//            intent.putExtra("name", mutableList[position].nameSong)
//            intent.putExtra("singer", mutableList[position].nameSinger)
//            intent.putExtra("image", mutableList[position].images)
//            context?.startActivity(intent)
        })
    }


    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtPosition: TextView = itemView.findViewById(R.id.txtPositionAS)
        val nameSong: TextView = itemView.findViewById(R.id.txtNameSongAS)
        val txtNameSinger: TextView = itemView.findViewById(R.id.txtNameSingerAS)
        val txtTimeAS: TextView = itemView.findViewById(R.id.txtTimeAS)

    }
}