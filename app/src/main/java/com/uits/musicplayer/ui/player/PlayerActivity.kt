package com.uits.musicplayer.ui.player


import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import com.uits.musicplayer.R
import java.io.IOException

class PlayerActivity : AppCompatActivity() {

    var mediaPlayer = MediaPlayer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fm, PlayerImageFragment.newInstance())
            .commitNow()
        Log.e("ppp","p")
        liric()
        play()
        back()
    }

    private fun liric() {
        val ibtnPlayerList = findViewById<ImageButton>(R.id.ibtnPlayerList)
        val ibtnPlayerAlbumList = findViewById<ImageButton>(R.id.ibtnPlayerAlbumList)

        ibtnPlayerList.setOnClickListener(View.OnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fm, PlayerFragment.newInstance())
                .commitNow()

        })
        ibtnPlayerAlbumList.setOnClickListener(View.OnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fm, PlayerImageFragment.newInstance())
                .commitNow()
        })
    }

    private fun back() {
        val backPlayer: AppCompatImageButton = findViewById(R.id.btnbackPlayer)
        backPlayer.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    private fun play() {
        var intent: Intent = intent
        var ibtnPausePlayer: ImageButton = findViewById(R.id.ibtnPausePlayer)
        var ibtnPlayPlayer: ImageButton = findViewById(R.id.ibtnPlayPlayer)
        var txtNameSongPlaylist: AppCompatTextView = findViewById(R.id.txtNameSongPlaylist)
        var txtNameSingerPlaylist: AppCompatTextView = findViewById(R.id.txtNameSingerPlaylist)

        var link: String = intent.getStringExtra("music").toString()
        var title2: String = intent.getStringExtra("name").toString()
        var singer: String = intent.getStringExtra("singer").toString()
        txtNameSongPlaylist.text = title2
        txtNameSingerPlaylist.text = singer

        try {
            val assetManager = assets
            val winSoundFile = assetManager.openFd(link)
            if (winSoundFile != null) {
                mediaPlayer.setDataSource(winSoundFile)
            }
            mediaPlayer.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Log.e("ppp",mediaPlayer.isPlaying.toString())
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            Log.e("ppp",mediaPlayer.isPlaying.toString())
        }
        mediaPlayer.start()

        ibtnPlayPlayer.setOnClickListener(View.OnClickListener {
            mediaPlayer.start()
            ibtnPlayPlayer.visibility = INVISIBLE
            ibtnPausePlayer.visibility = VISIBLE
        })
        ibtnPausePlayer.setOnClickListener(View.OnClickListener {
            mediaPlayer.pause()
            ibtnPlayPlayer.visibility = VISIBLE
            ibtnPausePlayer.visibility = INVISIBLE
        })


    }


}