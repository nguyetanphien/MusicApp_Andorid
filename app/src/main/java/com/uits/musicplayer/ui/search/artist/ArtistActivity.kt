package com.uits.musicplayer.ui.search.artist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uits.musicplayer.R
import com.uits.musicplayer.databinding.ActivityArtistBinding
import com.uits.musicplayer.interfaces.OnItemClickListener
import com.uits.musicplayer.model.AlbumModel
import com.uits.musicplayer.model.ArtistModel
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

class ArtistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArtistBinding
    private lateinit var viewModel: ArtistViewModel
    lateinit var artistAdapter: ArtistAdapter
    lateinit var artistAdapterPT: ArtistAdapterPT
    var mListData: MutableList<ArtistModel> = mutableListOf()
    var mListDataPT: MutableList<AlbumModel> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //   supportActionBar?.hide()
        binding = ActivityArtistBinding.inflate(layoutInflater)

        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(ArtistViewModel::class.java)
        backGroug()
        rVAlbum()
        rVAlbumPT()
        back()

    }

    @SuppressLint("SuspiciousIndentation")
    fun backGroug() {
        var backGroud: ImageView = findViewById(R.id.imgbackgroud)
        var nameSinger: TextView = findViewById(R.id.txtNameSinger)
        var txtBody: TextView = findViewById(R.id.txtBody)

        Glide.with(application)
            .load(intent.getStringExtra("image"))
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher)
            .into(backGroud)

        nameSinger.text = intent.getStringExtra("genre")
        txtBody.text =
            "."

    }

    fun rVAlbum() {
        val mRecyclerView: RecyclerView = binding.mRecycleViewAlbum
        val intent: Intent = intent
        val genre = intent.getStringExtra("genre")
        artistAdapter = ArtistAdapter(this, mListData, object : OnItemClickListener {
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
                TODO("Not yet implemented")
            }

        })
        if (genre != null) {
            viewModel.featchData(genre)
        }
        mRecyclerView.adapter = ScaleInAnimationAdapter(artistAdapter)
        mRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        viewModel._liveData.observe(this, Observer { data ->
            mListData.clear()
            mListData.addAll(data)
            artistAdapter.notifyDataSetChanged()
        })
    }

    private fun rVAlbumPT() {
        val mRecyclerViewPT = binding.mRecyclePopularTracks
        val intent: Intent = intent
        val genre = intent.getStringExtra("genre")
        artistAdapterPT = ArtistAdapterPT(this, mListDataPT, object : OnItemClickListener {
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
                TODO("Not yet implemented")
            }

        })
        if (genre != null) {
            viewModel.featchDataPT(genre)
        }
        mRecyclerViewPT.adapter = ScaleInAnimationAdapter(artistAdapterPT)
        mRecyclerViewPT.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewModel._listDataPT.observe(this, Observer { data ->
            mListDataPT.clear()
            mListDataPT.addAll(data)
            artistAdapterPT.notifyDataSetChanged()
        })
    }

    private fun back() {
        val imgbtnBack = binding.imgbtnBack
        imgbtnBack.setOnClickListener { finish() }
    }

}