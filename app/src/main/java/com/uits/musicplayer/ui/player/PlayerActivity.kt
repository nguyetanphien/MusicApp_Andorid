package com.uits.musicplayer.ui.player


import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
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
    private var soundId: Int = 0
    private var isSoundPlaying = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fm, PlayerImageFragment.newInstance()).commitNow()
        liric()
        play()
        back()
    }

    private fun liric() {
        val ibtnPlayerList = findViewById<ImageButton>(R.id.ibtnPlayerList)
        val ibtnPlayerAlbumList = findViewById<ImageButton>(R.id.ibtnPlayerAlbumList)

        ibtnPlayerList.setOnClickListener(View.OnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fm, PlayerFragment.newInstance())
                .commitNow()

        })
        ibtnPlayerAlbumList.setOnClickListener(View.OnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fm, PlayerImageFragment.newInstance()).commitNow()
        })
    }

    private fun back() {
        val backPlayer: AppCompatImageButton = findViewById(R.id.btnbackPlayer)
        backPlayer.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    private fun play() {
        val intent: Intent = intent

        val ibtnPausePlayer: ImageButton = findViewById(R.id.ibtnPausePlayer)
        val ibtnPlayPlayer: ImageButton = findViewById(R.id.ibtnPlayPlayer)
        val txtNameSongPlaylist: AppCompatTextView = findViewById(R.id.txtNameSongPlaylist)
        val txtNameSingerPlaylist: AppCompatTextView = findViewById(R.id.txtNameSingerPlaylist)

        val link: String = intent.getStringExtra("music").toString()
        val title2: String = intent.getStringExtra("name").toString()
        val singer: String = intent.getStringExtra("singer").toString()
        txtNameSongPlaylist.text = title2
        txtNameSingerPlaylist.text = singer

        val soundPool: SoundPool
        val audioAttributes =
            AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA).build()

        // Tạo một đối tượng SoundPool với các cài đặt AudioAttributes
        soundPool = SoundPool.Builder().setAudioAttributes(audioAttributes).build()

        // Tải tệp nhạc từ thư mục assets
        if (isSoundPlaying) {
            soundPool.stop(soundId)
            isSoundPlaying = false
        }
        try {
            val assetManager = application.assets
            val inputStream = assetManager.openFd(link)
            soundId = soundPool.load(inputStream, 1)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        soundPool.setOnLoadCompleteListener { _, _, _ ->
            soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
            isSoundPlaying = true
        }

        ibtnPlayPlayer.setOnClickListener(View.OnClickListener {
            soundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f)
            ibtnPlayPlayer.visibility = INVISIBLE
            ibtnPausePlayer.visibility = VISIBLE
            isSoundPlaying = true

        })
        ibtnPausePlayer.setOnClickListener(View.OnClickListener {
            soundPool.pause(soundId)
            ibtnPlayPlayer.visibility = VISIBLE
            ibtnPausePlayer.visibility = INVISIBLE
            isSoundPlaying = false

        })

    }
}