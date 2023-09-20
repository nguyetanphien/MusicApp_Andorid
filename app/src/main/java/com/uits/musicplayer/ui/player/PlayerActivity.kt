package com.uits.musicplayer.ui.player


import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.FragmentContainerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.uits.musicplayer.R
import java.io.IOException


class PlayerActivity : AppCompatActivity() {
    private var soundId: Int = 0
    private var isSoundPlaying = false
    private var mediaPlayer = MediaPlayer()
    val link: String = ""

    var image: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        val intent: Intent = intent
        image = intent.getStringExtra("image").toString()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fm, PlayerImageFragment.newInstance(image)).commitNow()
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
                .replace(R.id.fm, PlayerImageFragment.newInstance(image)).commitNow()
        })
    }

    private fun back() {
        val backPlayer: AppCompatImageButton = findViewById(R.id.btnbackPlayer)
        backPlayer.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    private fun play() {
        val ibtnPausePlayer: ImageButton = findViewById(R.id.ibtnPausePlayer)
        val ibtnPlayPlayer: ImageButton = findViewById(R.id.ibtnPlayPlayer)
        val txtTimeMax: AppCompatTextView = findViewById(R.id.txtTimeMax)
        val txtNameSongPlaylist: AppCompatTextView = findViewById(R.id.txtNameSongPlaylist)
        val txtNameSingerPlaylist: AppCompatTextView = findViewById(R.id.txtNameSingerPlaylist)
        val sbPlayer: SeekBar = findViewById(R.id.sbPlayer)
        val link: String = intent.getStringExtra("music").toString()
        val title2: String = intent.getStringExtra("name").toString()
        val singer: String = intent.getStringExtra("singer").toString()
        txtNameSongPlaylist.text = title2
        txtNameSingerPlaylist.text = singer
        Log.d("ppp", link)
        val audioAttributes =
            AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA).build()

        mediaPlayer.setAudioAttributes(audioAttributes)

        try {
            mediaPlayer.setDataSource(link)
            mediaPlayer.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        sbPlayer?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val newPosition = progress * mediaPlayer?.duration!! / 100
                    mediaPlayer?.seekTo(newPosition)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        mediaPlayer?.setOnPreparedListener {
            mediaPlayer?.start()
            val durationInMillis =
                mediaPlayer.duration ?: 0 // Thời gian bài hát tính bằng mili giây
            val minutes = (durationInMillis / 1000) / 60
            val seconds = (durationInMillis / 1000) % 60
            var m_s = "$minutes:$seconds"
            txtTimeMax.text = m_s// Bắt đầu phát nhạc sau khi chuẩn bị xong
            startTrackingTime()

        }
        isSoundPlaying = true
        ibtnPlayPlayer.setOnClickListener(View.OnClickListener {
            mediaPlayer.start()
            ibtnPlayPlayer.visibility = INVISIBLE
            ibtnPausePlayer.visibility = VISIBLE
            isSoundPlaying = true

        })
        ibtnPausePlayer.setOnClickListener(View.OnClickListener {
            mediaPlayer.pause()
            ibtnPlayPlayer.visibility = VISIBLE
            ibtnPausePlayer.visibility = INVISIBLE
            isSoundPlaying = false

        })

    }

    private fun startTrackingTime() {
        val handler = Handler(Looper.getMainLooper())
        val txtTimeNow: AppCompatTextView = findViewById(R.id.txtTimeNow)
        val sbPlayer: SeekBar = findViewById(R.id.sbPlayer)
        sbPlayer?.max = mediaPlayer.duration
        handler.postDelayed(object : Runnable {
            override fun run() {
                val currentPosition =
                    mediaPlayer?.currentPosition ?: 0 // Thời gian hiện tại tính bằng mili giây
                val minutes = (currentPosition / 1000) / 60
                val seconds = (currentPosition / 1000) % 60
                val m_s = "$minutes:$seconds"
                sbPlayer?.progress = currentPosition
                txtTimeNow.text = m_s
                handler.postDelayed(this, 1000) // Cập nhật thời gian mỗi giây
            }
        }, 0)
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



