package com.uits.musicplayer.ui.favorite.Track

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uits.musicplayer.R
import com.uits.musicplayer.databinding.ActivityArtistBinding
import com.uits.musicplayer.databinding.ActivityTracksBinding
import com.uits.musicplayer.interfaces.OnItemClickListener
import com.uits.musicplayer.model.ArtistModel
import com.uits.musicplayer.ui.player.PlayerActivity
import com.uits.musicplayer.ui.search.artist.ArtistAdapter
import com.uits.musicplayer.ui.search.artist.ArtistAdapterPT
import com.uits.musicplayer.ui.search.artist.ArtistViewModel
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

class TracksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTracksBinding
    private lateinit var viewModel: TrackViewModel
    lateinit var trackAdapter: TrackAdapter
    var mListData: MutableList<ArtistModel> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTracksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(TrackViewModel::class.java)
        rVTRack()
        back()
        playFTrack()
    }
    private fun rVTRack() {
        val mRecyclerView: RecyclerView = binding.rvFTrack
        trackAdapter = TrackAdapter(this, mListData, object : OnItemClickListener {


            override fun onItemClick(position: Int, id: String) {
                TODO("Not yet implemented")
            }

            override fun onItemClick2(position: Int, link: String, title: String, singer: String) {
                TODO("Not yet implemented")
            }

        })
        viewModel.fetchTrackList()
        mRecyclerView.adapter = ScaleInAnimationAdapter(trackAdapter)
        mRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewModel._listLive.observe(this, Observer { data ->
            mListData.clear()
            mListData.addAll(data)
            trackAdapter.notifyDataSetChanged()
        })
    }
    fun back(){
        val back: ImageButton=findViewById(R.id.ibtnBackFTrack)
        back.setOnClickListener(View.OnClickListener {
            finish()
        })
    }
    fun playFTrack(){
        val ibtnPlayFTrack :ImageButton=findViewById(R.id.ibtnPlayFTrack)
        ibtnPlayFTrack.setOnClickListener(View.OnClickListener {
            val intent= Intent(this,PlayerActivity::class.java)
            startActivity(intent)
        })

    }
}