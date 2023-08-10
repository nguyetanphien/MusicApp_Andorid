package com.uits.musicplayer.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uits.musicplayer.R
import com.uits.musicplayer.interfaces.OnItemClickListener
import com.uits.musicplayer.model.HomeModel
import com.uits.musicplayer.ui.album.AlbumActivity

class FavoriteAdapterAL(
    val context: FragmentActivity?,
    val mList: MutableList<HomeModel>,
    var onClick: OnItemClickListener
) : RecyclerView.Adapter<FavoriteAdapterAL.FavoriteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_albums_lb, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        if (context != null) {
            Glide.with(context)
                .load(mList[position].image)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.img)
        }

        holder.txtNameAlbum.text = mList[position].nameAlbum
        holder.txtYearAlbum.text = mList[position].yearAlbum
        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, AlbumActivity::class.java)
            context?.startActivity(intent)
        })
    }

    class FavoriteViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val img: ImageView = itemView.findViewById(R.id.imgAlbum)
        val txtNameAlbum: TextView = itemView.findViewById(R.id.txtNameAlbum)
        val txtYearAlbum: TextView = itemView.findViewById(R.id.txtYearAlbum)
    }
}