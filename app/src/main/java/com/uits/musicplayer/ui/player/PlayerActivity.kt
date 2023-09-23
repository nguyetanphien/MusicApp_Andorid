package com.uits.musicplayer.ui.player


import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
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
import com.uits.musicplayer.ui.player.MediaPlayerManager.seekbar
import com.uits.musicplayer.ui.player.MediaPlayerManager.startMusic
import com.uits.musicplayer.ui.player.MediaPlayerManager.startTrackingTime
import java.io.IOException


class PlayerActivity : AppCompatActivity() {
    private var soundId: Int = 0
    private var isSoundPlaying = false

    var image: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        val intent: Intent = intent
        image = intent.getStringExtra("image").toString()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fm, PlayerImageFragment.newInstance(image)).commitNow()
        val link: String = intent.getStringExtra("music").toString()
        val title2: String = intent.getStringExtra("name").toString()
        val singer: String = intent.getStringExtra("singer").toString()
        liric()
        play(link, title2, singer)
        back(title2, link, singer, image)
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
        var play: String = if (startMusic()) {
            "ok"
        } else
            "no"
        backPlayer.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.putExtra("play", play)
            intent.putExtra("music", link)
            intent.putExtra("name", title2)
            intent.putExtra("singer", singer)
            intent.putExtra("image", images)
            setResult(RESULT_OK, intent)
            finish()
        })
    }

    private fun play(link: String, title2: String, singer: String) {
        val ibtnPausePlayer: ImageButton = findViewById(R.id.ibtnPausePlayer)
        val ibtnPlayPlayer: ImageButton = findViewById(R.id.ibtnPlayPlayer)
        val txtTimeMax: AppCompatTextView = findViewById(R.id.txtTimeMax)
        val txtNameSongPlaylist: AppCompatTextView = findViewById(R.id.txtNameSongPlaylist)
        val txtNameSingerPlaylist: AppCompatTextView = findViewById(R.id.txtNameSingerPlaylist)
        val sbPlayer: SeekBar = findViewById(R.id.sbPlayer)
        val txtTimeNow: AppCompatTextView = findViewById(R.id.txtTimeNow)

        txtNameSongPlaylist.text = title2
        txtNameSingerPlaylist.text = singer
        Log.d("ppp", link)
        MediaPlayerManager.playMusic(link, txtTimeMax)
        startTrackingTime(sbPlayer, txtTimeNow)
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

object MediaPlayerManager {
    private var mediaPlayer = MediaPlayer()

    fun playMusic(link: String, txtTimeMax: AppCompatTextView) {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.reset()
        try {
            mediaPlayer.setDataSource(link)
            mediaPlayer.prepare()
            timeMusic(txtTimeMax)
            mediaPlayer.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun pauseMusic() {
        mediaPlayer.pause()
    }

    fun resumeMusic() {
        mediaPlayer.start()
    }

    fun startMusic(): Boolean {
        if (mediaPlayer.isPlaying) {
            return true
        }
        return false
    }

    fun stopMusic() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
    }

    fun releaseMediaPlayer() {
        mediaPlayer.release()
    }

    fun timeMusic(txtTimeMax: AppCompatTextView) {
        var m_s = ""
        mediaPlayer.setOnPreparedListener {
            val durationInMillis =
                mediaPlayer.duration ?: 0 // Thời gian bài hát tính bằng mili giây
            val minutes = (durationInMillis / 1000) / 60
            val seconds = (durationInMillis / 1000) % 60
            m_s = String.format("%02d:%02d", minutes, seconds)
            txtTimeMax.text = m_s// Bắt đầu phát nhạc sau khi chuẩn bị xong
        }
    }

    fun startTrackingTime(sbPlayer: SeekBar, txtTimeNow: AppCompatTextView) {
        val handler = Handler(Looper.getMainLooper())

        sbPlayer?.max = mediaPlayer.duration
        handler.postDelayed(object : Runnable {
            override fun run() {
                val currentPosition =
                    mediaPlayer?.currentPosition ?: 0 // Thời gian hiện tại tính bằng mili giây
                val minutes = (currentPosition / 1000) / 60
                val seconds = (currentPosition / 1000) % 60
                val m_s = String.format("%02d:%02d", minutes, seconds)
                sbPlayer?.progress = currentPosition
                txtTimeNow.text = m_s
                handler.postDelayed(this, 1000) // Cập nhật thời gian mỗi giây
            }
        }, 0)
    }

    fun seekbar(sbPlayer: SeekBar) {
        sbPlayer.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    // Tính toán thời gian mới dựa trên tiến trình của SeekBar
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Bắt đầu theo dõi khi người dùng chạm vào SeekBar
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Kết thúc theo dõi khi người dùng thả SeekBar
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.start()
                } else {
                    mediaPlayer.prepare()
                    mediaPlayer.start()
                }
            }
        })


    }
}



