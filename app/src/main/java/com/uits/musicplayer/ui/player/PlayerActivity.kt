package com.uits.musicplayer.ui.player


import android.annotation.SuppressLint
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
import androidx.appcompat.widget.AppCompatTextView
import com.uits.musicplayer.R
import com.uits.musicplayer.database.entities.Favorite
import com.uits.musicplayer.database.entities.RecentListenings
import com.uits.musicplayer.database.repository.FavoriteRepository
import com.uits.musicplayer.database.repository.RecentListeningRepository
import com.uits.musicplayer.model.AlbumModel
import com.uits.musicplayer.ui.player.MediaPlayerManager.seekbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.IOException
import kotlin.random.Random


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
    private var isPlaying = false

    fun playMusic(
        musicList: MutableList<AlbumModel>,

        currentTrackIndex: Int,
        txtTimeMax: AppCompatTextView,
        txtNameSongPlaylist: AppCompatTextView,
        txtNameSingerPlaylist: AppCompatTextView,
        ibtnBackSongPlayer: AppCompatImageButton,
        ibtnNextSongPlayer: AppCompatImageButton,
        ibtnRepeatSongPlayer: AppCompatImageButton,
        ibtnShuffle: AppCompatImageButton,
        txtTimeNow: AppCompatTextView,
        sbPlayer: SeekBar,
        application: Application,
        ibtnUnFavorite: ImageButton,
        ibtnFavorite: ImageButton
    ) {
        var next = currentTrackIndex
        val recentHistoryRespository = RecentListeningRepository(application)

        fun insert(recentListenings: RecentListenings) =
            MainScope().launch(Dispatchers.IO) {
                recentHistoryRespository.insert(recentListenings)
            }
        if (musicList!=null)
        if (next < musicList.size) {
            var currentTrack = musicList[next]
            mediaPlayer.reset()
            mediaPlayer.setDataSource(currentTrack.link)
            mediaPlayer.prepare()
            timeMusic(txtTimeMax)
            txtNameSongPlaylist.text = musicList[next].nameSong
            txtNameSingerPlaylist.text = musicList[next].nameSinger
            startTrackingTime(sbPlayer, txtTimeNow)
            mediaPlayer.start()
            var link = currentTrack.link
            var nameSong = currentTrack.nameSong
            var image = currentTrack.images
            var singer = currentTrack.nameSinger
            var time = currentTrack.time
            val recentListenings = RecentListenings()
            recentListenings.id = Random.nextInt().toString()
            recentListenings.title = nameSong
            recentListenings.title = nameSong
            recentListenings.link = link
            recentListenings.images = image
            recentListenings.time = time
            insert(recentListenings)

            var check = false
            ibtnRepeatSongPlayer.setOnClickListener(View.OnClickListener {
                if (!check) {
                    check = true
                    ibtnRepeatSongPlayer.visibility = View.INVISIBLE
                    ibtnShuffle.visibility = View.VISIBLE
                }
            })
            ibtnShuffle.setOnClickListener(View.OnClickListener {
                check = false
                next = (0 until (musicList.size)).random()
                ibtnRepeatSongPlayer.visibility = View.VISIBLE
                ibtnShuffle.visibility = View.INVISIBLE

            })

            ibtnBackSongPlayer.setOnClickListener(View.OnClickListener {
                next--

                if (next < 0) {
                    next = 0
                }
                playMusic(
                    musicList,

                    next,
                    txtTimeMax,
                    txtNameSongPlaylist,
                    txtNameSingerPlaylist,
                    ibtnBackSongPlayer,
                    ibtnNextSongPlayer,
                    ibtnRepeatSongPlayer,
                    ibtnShuffle, txtTimeNow,
                    sbPlayer,
                    application,
                    ibtnUnFavorite,
                    ibtnFavorite
                )
            })
            ibtnFavorite.setOnClickListener(View.OnClickListener {
                val favoriteRepository = FavoriteRepository(
                    application
                )
                fun insert(favorite: Favorite) =
                    MainScope().launch(Dispatchers.IO) {
                        favoriteRepository.insert(favorite)
                    }

                var favorite = Favorite()
                favorite.id = Random.nextInt().toString()
                favorite.images = image
                favorite.time = time
                favorite.singer = singer
                favorite.title = nameSong
                favorite.link = link
                insert(favorite)

                ibtnFavorite.visibility= INVISIBLE

            })
            ibtnUnFavorite.setOnClickListener(View.OnClickListener {

            })
            Log.d("ppp", "t$currentTrackIndex")
            ibtnNextSongPlayer.setOnClickListener(View.OnClickListener {
                next++

                if (next > musicList.size) {
                    next = musicList.size
                }
                playMusic(
                    musicList,

                    next,
                    txtTimeMax,
                    txtNameSongPlaylist,
                    txtNameSingerPlaylist,
                    ibtnBackSongPlayer,
                    ibtnNextSongPlayer,
                    ibtnRepeatSongPlayer,
                    ibtnShuffle, txtTimeNow,
                    sbPlayer,
                    application,
                    ibtnUnFavorite,
                    ibtnFavorite
                )
            })
            // Nghe sự kiện kết thúc bài hát
            mediaPlayer.setOnCompletionListener {
                if (check) {
                    if (next >= musicList.size) {
                        next = 0
                        playMusic(
                            musicList,

                            next,
                            txtTimeMax,
                            txtNameSongPlaylist,
                            txtNameSingerPlaylist,
                            ibtnBackSongPlayer,
                            ibtnNextSongPlayer,
                            ibtnRepeatSongPlayer,
                            ibtnShuffle, txtTimeNow,
                            sbPlayer,
                            application,
                            ibtnUnFavorite,
                            ibtnFavorite
                        )
                    }
                } else {
                    next++
                    if (next < musicList.size) {

                        playMusic(
                            musicList,

                            next,
                            txtTimeMax,
                            txtNameSongPlaylist,
                            txtNameSingerPlaylist,
                            ibtnBackSongPlayer,
                            ibtnNextSongPlayer,
                            ibtnRepeatSongPlayer,
                            ibtnShuffle, txtTimeNow,
                            sbPlayer,
                            application,
                            ibtnUnFavorite,
                            ibtnFavorite
                        )
                    } else {
                        if (next >= musicList.size) {
                            next = 0
                            playMusic(
                                musicList,

                                next,
                                txtTimeMax,
                                txtNameSongPlaylist,
                                txtNameSingerPlaylist,
                                ibtnBackSongPlayer,
                                ibtnNextSongPlayer,
                                ibtnRepeatSongPlayer,
                                ibtnShuffle, txtTimeNow,
                                sbPlayer,
                                application,
                                ibtnUnFavorite,
                                ibtnFavorite
                            )
                        }
                    }
                }

            }
        }
    }

    @Synchronized
    fun pauseMusic() {
        mediaPlayer.pause()
        isPlaying = false
    }

    @Synchronized
    fun resumeMusic() {
        mediaPlayer.start()
        isPlaying = false
    }

    @Synchronized
    fun startMusic() {
        if (mediaPlayer != null && !isPlaying) {
            isPlaying = true
        }
    }

    @Synchronized
    fun stopMusic() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            isPlaying = false
        }
    }

    @Synchronized
    fun releaseMediaPlayer() {
        mediaPlayer.release()
        isPlaying = false
    }

    @Synchronized
    fun isMusicPlaying(): Boolean {
        return isPlaying
    }

    private fun timeMusic(txtTimeMax: AppCompatTextView) {
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



