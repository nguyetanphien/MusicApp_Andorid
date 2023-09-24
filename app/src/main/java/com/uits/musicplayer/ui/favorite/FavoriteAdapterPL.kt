package com.uits.musicplayer.ui.favorite

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
import com.uits.musicplayer.model.HomeModel
import com.uits.musicplayer.ui.album.AlbumActivity

class FavoriteAdapterPL(
    val context: FragmentActivity?,
    private val mList: MutableList<HomeModel>,
    var onClick: OnItemClickListener
) : RecyclerView.Adapter<FavoriteAdapterPL.HomeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_playlists, parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        if (context != null) {
            Glide.with(context)
                .load(mList[position].image)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.img)
        }

        holder.txtNameAlbumH.text = mList[position].nameAlbum
        holder.txtYearH.text = mList[position].yearAlbum
        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, AlbumActivity::class.java)
            intent.putExtra("album", mList[position].nameAlbum)
            context?.startActivity(intent)
        })
    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.imgAlbumH)
        val txtNameAlbumH: TextView = itemView.findViewById(R.id.txtNameAlbumH)
        val txtYearH: TextView = itemView.findViewById(R.id.txtYearAlbumH)

    }
}