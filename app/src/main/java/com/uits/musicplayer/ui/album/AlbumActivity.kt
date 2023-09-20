package com.uits.musicplayer.ui.album


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uits.musicplayer.R
import com.uits.musicplayer.databinding.ActivityAlbumBinding
import com.uits.musicplayer.interfaces.OnItemClickListener
import com.uits.musicplayer.model.AlbumModel
import com.uits.musicplayer.ui.player.PlayerActivity

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter


class AlbumActivity : AppCompatActivity() {
    lateinit var binding: ActivityAlbumBinding
    private lateinit var viewModel: AlbumViewModel
    lateinit var abbumAdapter: AlbumAdapter
    var mutableList: MutableList<AlbumModel> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(AlbumViewModel::class.java)
        rVAlbumList()
        back()
        playMusic()
    }

    private fun rVAlbumList() {
        val intent: Intent = intent
        val name = intent.getStringExtra("album")

        val mRecyclerView: RecyclerView = binding.rvAlbumAS
        val txtAlbumNameYearAS: AppCompatTextView = findViewById(R.id.txtAlbumNameYearAS)
        txtAlbumNameYearAS.text = "Album • $name"
        abbumAdapter = AlbumAdapter(this, mutableList, object : OnItemClickListener {
            override fun onItemClick(position: Int, id: String) {
            }

            override fun onItemClick2(
                position: Int,
                link: String,
                name: String,
                singer: String,
                images: String
            ) {
                val intent = Intent(applicationContext, PlayerActivity::class.java)
                intent.putExtra("music", link)
                intent.putExtra("name", name)
                intent.putExtra("singer", singer)
                intent.putExtra("image",images)
                startActivity(intent)
            }
        })
        if (name != null) {
            viewModel.featchData(name)
        }
        mRecyclerView.adapter = ScaleInAnimationAdapter(abbumAdapter)
        mRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewModel.liveData.observe(this, Observer { data ->
            mutableList.clear()
            mutableList.addAll(data)
            abbumAdapter.notifyDataSetChanged()
        })
    }

    private fun back() {
        val back: ImageButton = findViewById(R.id.ibtnBackAlbumAS)
        back.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    private fun playMusic() {
        val imagePlay: ImageButton = findViewById(R.id.ibtnPlayAS)
        imagePlay.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, PlayerActivity::class.java)
            intent.putExtra("music", "a.mp3")
            startActivity(intent)
        })
    }
}