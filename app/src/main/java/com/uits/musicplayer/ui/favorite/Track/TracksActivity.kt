package com.uits.musicplayer.ui.favorite.Track

import android.content.ClipData
import android.content.Intent
import android.media.RouteListingPreference.Item
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uits.musicplayer.R
import com.uits.musicplayer.database.entities.Favorite
import com.uits.musicplayer.database.repository.FavoriteRepository
import com.uits.musicplayer.databinding.ActivityTracksBinding
import com.uits.musicplayer.interfaces.OnItemClickListener
import com.uits.musicplayer.model.AlbumModel
import com.uits.musicplayer.model.ArtistModel
import com.uits.musicplayer.ui.player.PlayerActivity
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class TracksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTracksBinding
    private lateinit var viewModel: TrackViewModel
    lateinit var trackAdapter: TrackAdapter
    var mListData: MutableList<Favorite> = mutableListOf()
    var listAlbum: MutableList<AlbumModel> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTracksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[TrackViewModel::class.java]
        rVTRack()
        back()
        playFTrack()
    }

    private fun rVTRack() {
        val mRecyclerView: RecyclerView = binding.rvFTrack
        trackAdapter = TrackAdapter(this, mListData, object : OnItemClickListener {


            override fun onItemClick(
                position: Int,
                id: String,
                button: ImageButton,
                link: String,
                title: String,
                singer: String,
                images: String
            ) {
                val popupMenu = PopupMenu(applicationContext, button)
                popupMenu.inflate(R.menu.menu)
                Log.d("ppp", "dd")
                popupMenu.menu.findItem(R.id.itemUnFavorite).isVisible = true
                popupMenu.menu.findItem(R.id.itemFavorite).isVisible = false

                popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->

                    if (menuItem.itemId == R.id.itemUnFavorite) {
                        viewModel.deleteid(id)
                    }
                    false
                }
                popupMenu.show()
            }

            override fun onItemClick2(
                position: Int,
                id: String,
                link: String,
                title: String,
                singer: String,
                images: String
            ) {
                    val intent = Intent(applicationContext, PlayerActivity::class.java)
                    intent.putParcelableArrayListExtra("listMusic", ArrayList(listAlbum))
                    intent.putExtra("position", position)
                    startActivity(intent)




            }

        })

        mRecyclerView.adapter = ScaleInAnimationAdapter(trackAdapter)
        mRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        // viewModel.loadData()
        viewModel.getDAta().observe(this, Observer { data ->
            mListData.clear()
            mListData.addAll(data)
            trackAdapter.notifyDataSetChanged()
            mListData.forEach {
                val id = it.id
                Log.d("ppp", id.toString())

                val name = it.title
                val link = it.link
                val singer = it.singer
                val time = it.time
                val images = it.images
                listAlbum.add(AlbumModel(id, name, singer, link, time, images))
            }
            Log.d("ppp", listAlbum.size.toString())
        })

    }

    fun back() {
        val back: ImageButton = findViewById(R.id.ibtnBackFTrack)
        back.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    fun playFTrack() {
        val ibtnPlayFTrack: ImageButton = findViewById(R.id.ibtnPlayFTrack)
        ibtnPlayFTrack.setOnClickListener(View.OnClickListener {
            if (listAlbum.isNotEmpty()){
                val intent = Intent(this, PlayerActivity::class.java)
                intent.putParcelableArrayListExtra("listMusic", ArrayList(listAlbum))
                startActivity(intent)
            }
            else{
                Toast.makeText(applicationContext,R.string.No_songs, Toast.LENGTH_SHORT).show()
            }

        })

    }
}


