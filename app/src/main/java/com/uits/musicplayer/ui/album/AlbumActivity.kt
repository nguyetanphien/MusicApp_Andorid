package com.uits.musicplayer.ui.album


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isInvisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uits.musicplayer.R
import com.uits.musicplayer.databinding.ActivityAlbumBinding
import com.uits.musicplayer.interfaces.OnItemClickListener
import com.uits.musicplayer.model.AlbumModel
import com.uits.musicplayer.ui.player.MediaPlayerManager

import com.uits.musicplayer.ui.player.MediaPlayerManager.pauseMusic
import com.uits.musicplayer.ui.player.MediaPlayerManager.resumeMusic
import com.uits.musicplayer.ui.player.MediaPlayerManager.startMusic

import com.uits.musicplayer.ui.player.PlayerActivity

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import java.io.IOException


class AlbumActivity : AppCompatActivity() {
    lateinit var binding: ActivityAlbumBinding
    private lateinit var viewModel: AlbumViewModel
    private lateinit var abbumAdapter: AlbumAdapter
    var mutableList: MutableList<AlbumModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[AlbumViewModel::class.java]
        val rlAlbumAS: RelativeLayout = binding.rlAlbum1AS
        rVAlbumList()
        if (startMusic()) rlAlbumAS.visibility = View.VISIBLE

        back("Awaken", "ntp", "https://storage.googleapis.com/automotive-media/album_art.jpg")
        playAndPause()

    }

    private fun rVAlbumList() {
        val intent: Intent = intent
        val name = intent.getStringExtra("album")
        val imgAvataSingerAS: ImageView = findViewById(R.id.imgAvataSingerAS)
        val txtNameAlbumAS: TextView = findViewById(R.id.txtNameAlbumAS)
        val txtNameSingerAS: TextView = findViewById(R.id.txtNameSingerAS)
        val txtAlbumTimeAS: TextView = findViewById(R.id.txtAlbumTimeAS)
        val mRecyclerView: RecyclerView = binding.rvAlbumAS
        val txtAlbumNameYearAS: AppCompatTextView = findViewById(R.id.txtAlbumNameYearAS)
        txtAlbumNameYearAS.text = "Album • $name"
        abbumAdapter = AlbumAdapter(this, mutableList, object : OnItemClickListener {
            override fun onItemClick(position: Int, id: String) {
            }

            override fun onItemClick2(
                position: Int, link: String, name: String, singer: String, images: String
            ) {
                val intent = Intent(application, PlayerActivity::class.java)
                intent.putExtra("music", mutableList[position].link)
                intent.putExtra("name", mutableList[position].nameSong)
                intent.putExtra("singer", mutableList[position].nameSinger)
                intent.putExtra("image", mutableList[position].images)
                startActivityForResult(intent, 1)

            }
        })
        if (name != null) {
           // viewModel.featchData(name)

                viewModel.loadDataFireBase(name)


        }
        mRecyclerView.adapter = ScaleInAnimationAdapter(abbumAdapter)
        try {
            mRecyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        } catch (e: IOException) {

        }

        viewModel.liveData.observe(this, Observer { data ->
            mutableList.clear()
            mutableList.addAll(data)
            abbumAdapter.notifyDataSetChanged()
            try {
                txtNameAlbumAS.text = mutableList[0].nameSong
                txtNameSingerAS.text = mutableList[0].nameSinger
                Glide.with(application).load(mutableList[0].images).centerCrop()
                    .placeholder(R.mipmap.ic_launcher).into(imgAvataSingerAS)
                playMusic(
                    mutableList[0].link,
                    mutableList[0].nameSong,
                    mutableList[0].nameSinger,
                    mutableList[0].images
                )
                txtAlbumTimeAS.text = mutableList.size.toString() + " songs"
            } catch (e: IOException) {

            }

        })


    }


    private fun playAndPause() {
        val ibtnPauseSongAlbumAS: ImageButton = findViewById(R.id.ibtnPauseSongAlbumAS)
        val ibtnPlaySongAlbumAS: ImageButton = findViewById(R.id.ibtnPlaySongAlbumAS)
//        if(MediaPlayerManager.startMusic()){
//            ibtnPauseSongAlbumAS.visibility = View.VISIBLE
//            ibtnPlaySongAlbumAS.visibility = View.INVISIBLE
//        }
//        else{
//            ibtnPauseSongAlbumAS.visibility = View.INVISIBLE
//            ibtnPlaySongAlbumAS.visibility = View.VISIBLE
//        }

        ibtnPauseSongAlbumAS.setOnClickListener(View.OnClickListener {
            pauseMusic()
            ibtnPauseSongAlbumAS.visibility = View.INVISIBLE
            ibtnPlaySongAlbumAS.visibility = View.VISIBLE
        })
        ibtnPlaySongAlbumAS.setOnClickListener(View.OnClickListener {
            resumeMusic()
            ibtnPauseSongAlbumAS.visibility = View.VISIBLE
            ibtnPlaySongAlbumAS.visibility = View.INVISIBLE
        })
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var play: String
        val imgSongAlbumAS: AppCompatImageView = binding.imgSongAlbumAS
        val txtNameSongAlbumAS: AppCompatTextView = binding.txtNameSongAlbumAS
        val txtNameSingerAlbumAS: AppCompatTextView = binding.txtNameSingerAlbumAS
        val rlAlbumAS: RelativeLayout = binding.rlAlbum1AS

        Log.d("ppp", requestCode.toString())
        Log.d("ppp", requestCode.toString())
        if (resultCode == RESULT_OK && requestCode == 1) {
            play = data?.getStringExtra("play").toString()
            val nameSong = data?.getStringExtra("name")
            val nameSinger = data?.getStringExtra("singer")
            val image = data?.getStringExtra("image")
            if (image != null) {
                if (nameSinger != null) {
                    if (nameSong != null) {
                        back(nameSong, nameSinger, image)
                    }
                }
            }
            Log.d("ppp", play)
            if (play == "ok") {
                rlAlbumAS.visibility = View.VISIBLE
                Glide.with(application).load(image).centerCrop().placeholder(R.mipmap.ic_launcher)
                    .into(imgSongAlbumAS)
                txtNameSongAlbumAS.text = nameSong
                txtNameSingerAlbumAS.text = nameSinger


            } else {
                rlAlbumAS.visibility = View.INVISIBLE

            }

            Log.d("ppp", play)
        }
    }

    private fun back(title2: String, singer: String, images: String) {
        val back: ImageButton = findViewById(R.id.ibtnBackAlbumAS)
        var play: String = if (startMusic()) {
            "ok"
        } else "no"
        back.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.putExtra("play", play)
            intent.putExtra("name", title2)
            intent.putExtra("singer", singer)
            intent.putExtra("image", images)
            setResult(RESULT_OK, intent)
            finish()
        })
    }

    private fun playMusic(
        linkSong: String, nameSong: String, singerSong: String, imagesSong: String
    ) {
        val imagePlay: ImageButton = findViewById(R.id.ibtnPlayAS)

        imagePlay.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, PlayerActivity::class.java)
            intent.putExtra("music", linkSong)
            intent.putExtra("name", nameSong)
            intent.putExtra("singer", singerSong)
            intent.putExtra("image", imagesSong)
            startActivityForResult(intent, 1)
        })
    }
}