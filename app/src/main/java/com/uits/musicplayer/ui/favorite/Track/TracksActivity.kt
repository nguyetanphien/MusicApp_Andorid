package com.uits.musicplayer.ui.favorite.Track

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uits.musicplayer.R
import com.uits.musicplayer.database.entities.Favorite
import com.uits.musicplayer.databinding.ActivityTracksBinding
import com.uits.musicplayer.interfaces.OnItemClickListener
import com.uits.musicplayer.model.AlbumModel
import com.uits.musicplayer.model.ArtistModel
import com.uits.musicplayer.ui.player.PlayerActivity
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

class TracksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTracksBinding
    private lateinit var viewModel: TrackViewModel
    lateinit var trackAdapter: TrackAdapter
    var mListData: MutableList<Favorite> = mutableListOf()
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


            override fun onItemClick(position: Int, id: String) {
                TODO("Not yet implemented")
            }

            override fun onItemClick2(
                position: Int,
                link: String,
                title: String,
                singer: String,
                images: String
            ) {

                val intent = Intent(applicationContext, PlayerActivity::class.java)
                intent.putParcelableArrayListExtra("listMusic2", ArrayList(mListData))
                Log.d("pppp",ArrayList(mListData).size.toString())
                startActivity(intent)
            }

        })

        mRecyclerView.adapter = ScaleInAnimationAdapter(trackAdapter)
        mRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewModel.getDAta().observe(this, Observer { data ->
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


