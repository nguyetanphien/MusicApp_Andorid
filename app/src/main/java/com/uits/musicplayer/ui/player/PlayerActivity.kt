package com.uits.musicplayer.ui.player


import android.annotation.SuppressLint

import android.content.Intent
import android.media.AudioAttributes

import android.media.SoundPool
import android.os.Bundle

import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView

import com.uits.musicplayer.R

import com.uits.musicplayer.model.AlbumModel
import com.uits.musicplayer.ui.player.MediaPlayerManager.seekbar

import java.io.IOException


class PlayerActivity : AppCompatActivity() {
    private var soundId: Int = 0
    private var isSoundPlaying = false

    // lateinit var application:PlayerActivity
    var image: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        val intent: Intent = intent
        var listMusic = intent.getParcelableArrayListExtra<AlbumModel>("listMusic")
        var currentTrackIndex = intent.getIntExtra("position", 0)


        if (!listMusic.isNullOrEmpty()) {
            if (currentTrackIndex != null) {

                play(listMusic, currentTrackIndex)

            }
        }

        image = currentTrackIndex?.let { listMusic?.get(it)?.images } ?: ""
        supportFragmentManager.beginTransaction()
            .replace(R.id.fm, PlayerImageFragment.newInstance(image)).commitNow()
        liric()

        listMusic?.get(currentTrackIndex)?.let { back(it.nameSong, it.link, it.nameSinger, image) }
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
                .replace(R.id.fm, PlayerImageFragment.newInstance(image)).commitNow()
        })
    }

    @SuppressLint("SuspiciousIndentation")
    private fun back(title2: String, link: String, singer: String, images: String) {
        val backPlayer: AppCompatImageButton = findViewById(R.id.btnbackPlayer)

        backPlayer.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.putExtra("play", "ok")
            intent.putExtra("music", link)
            intent.putExtra("name", title2)
            intent.putExtra("singer", singer)
            intent.putExtra("image", images)
            setResult(RESULT_OK, intent)
            finish()
        })
    }

    private fun play(list: MutableList<AlbumModel>, currentTrackIndex: Int) {
        val ibtnPausePlayer: ImageButton = findViewById(R.id.ibtnPausePlayer)
        val ibtnPlayPlayer: ImageButton = findViewById(R.id.ibtnPlayPlayer)
        val ibtnNextSongPlayer: AppCompatImageButton = findViewById(R.id.ibtnNextSongPlayer)
        val ibtnBackSongPlayer: AppCompatImageButton = findViewById(R.id.ibtnBackSongPlayer)
        val ibtnRepeatSongPlayer: AppCompatImageButton = findViewById(R.id.ibtnRepeatSongPlayer)
        val ibtnShuffle: AppCompatImageButton = findViewById(R.id.ibtnShuffle)
        val txtTimeMax: AppCompatTextView = findViewById(R.id.txtTimeMax)
        val txtNameSongPlaylist: AppCompatTextView = findViewById(R.id.txtNameSongPlaylist)
        val txtNameSingerPlaylist: AppCompatTextView = findViewById(R.id.txtNameSingerPlaylist)
        val sbPlayer: SeekBar = findViewById(R.id.sbPlayer)
        val txtTimeNow: AppCompatTextView = findViewById(R.id.txtTimeNow)
        val ibtnUnFavorite: ImageButton = findViewById(R.id.ibtnUnFavorite)
        val ibtnFavorite: ImageButton = findViewById(R.id.ibtnFavorite)

        MediaPlayerManager.playMusic(
            list,
            currentTrackIndex,
            txtTimeMax,
            txtNameSongPlaylist,
            txtNameSingerPlaylist,
            ibtnBackSongPlayer,
            ibtnNextSongPlayer,
            ibtnRepeatSongPlayer,
            ibtnShuffle,
            txtTimeNow,
            sbPlayer, application, ibtnUnFavorite, ibtnFavorite
        )
        //startTrackingTime(sbPlayer, txtTimeNow)
        seekbar(sbPlayer)
        ibtnPlayPlayer.setOnClickListener(View.OnClickListener {
            MediaPlayerManager.resumeMusic()
            ibtnPlayPlayer.visibility = INVISIBLE
            ibtnPausePlayer.visibility = VISIBLE
        })
        ibtnPausePlayer.setOnClickListener(View.OnClickListener {
            MediaPlayerManager.pauseMusic()
            ibtnPlayPlayer.visibility = VISIBLE
            ibtnPausePlayer.visibility = INVISIBLE
        })

    }

    fun checkFavorite() {

    }

    fun playMusicAsset(link: String) {
        val ibtnPausePlayer: ImageButton = findViewById(R.id.ibtnPausePlayer)
        val ibtnPlayPlayer: ImageButton = findViewById(R.id.ibtnPlayPlayer)
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





