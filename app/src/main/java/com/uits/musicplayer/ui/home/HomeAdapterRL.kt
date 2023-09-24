package com.uits.musicplayer.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uits.musicplayer.R
import com.uits.musicplayer.database.entities.RecentListenings
import com.uits.musicplayer.interfaces.OnItemClickListener
import com.uits.musicplayer.model.HomeModel
import com.uits.musicplayer.ui.player.PlayerActivity

class HomeAdapterRL(
    val context: FragmentActivity?,
    val mListRL: MutableList<RecentListenings>,
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
                .load(mListRL[position].images)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imgRL)
        }
        holder.txtName.text = mListRL[position].title
        holder.txtTime.text = mListRL[position].time
        val p=position+1
        holder.txtstt.text=p.toString()
        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, PlayerActivity::class.java)
            context?.startActivity(intent)
        })
    }

    class HomeRLAdapter(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val txtstt: TextView= itemView.findViewById(R.id.txtStt)
        val imgRL: ImageView = itemview.findViewById(R.id.imgPopularTrack)
        val txtName: TextView = itemview.findViewById(R.id.txtNamePT)
        val txtTime: TextView = itemview.findViewById(R.id.txtTime)
    }

}