package com.uits.musicplayer.ui.album

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
import com.uits.musicplayer.databinding.ActivityAlbumBinding
import com.uits.musicplayer.interfaces.OnItemClickListener
import com.uits.musicplayer.model.AlbumModel
import com.uits.musicplayer.ui.favorite.Track.TrackAdapter
import com.uits.musicplayer.ui.favorite.Track.TrackViewModel
import com.uits.musicplayer.ui.player.PlayerActivity
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

class AlbumActivity : AppCompatActivity() {
    lateinit var binding: ActivityAlbumBinding
    private lateinit var viewModel:AlbumViewModel
    lateinit var abbumAdapter: AlbumAdapter
    var mutableList: MutableList<AlbumModel> = mutableListOf()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel=ViewModelProvider(this).get(AlbumViewModel::class.java)
        rVAlbumList()
        back()
        playMusic()
    }
    private fun rVAlbumList() {
        val mRecyclerView: RecyclerView = binding.rvAlbumAS
        abbumAdapter = AlbumAdapter(this, mutableList, object : OnItemClickListener {
            override fun onItemClick(position: Int) {

            }

        })
        viewModel.fetchAlbumList()
        mRecyclerView.adapter = ScaleInAnimationAdapter(abbumAdapter)
        mRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewModel.liveData.observe(this, Observer { data ->
            mutableList.clear()
            mutableList.addAll(data)
            abbumAdapter.notifyDataSetChanged()
        })
       // viewModel.loadSounds()
    }
    fun back(){
        val back: ImageButton =findViewById(R.id.ibtnBackAlbumAS)
        back.setOnClickListener(View.OnClickListener {
            finish()
        })
    }
    fun playMusic(){
        val imagePlay :ImageButton =findViewById(R.id.ibtnPlayAS)
        imagePlay.setOnClickListener(View.OnClickListener {
            val intent= Intent(this,PlayerActivity::class.java)
            startActivity(intent)
        })
    }
}