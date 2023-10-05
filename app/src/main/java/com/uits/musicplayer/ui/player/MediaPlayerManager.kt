package com.uits.musicplayer.ui.player

import android.app.Application
import android.content.Intent
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.uits.musicplayer.MainActivity
import com.uits.musicplayer.database.entities.Favorite
import com.uits.musicplayer.database.entities.RecentListenings
import com.uits.musicplayer.database.repository.FavoriteRepository
import com.uits.musicplayer.database.repository.RecentListeningRepository
import com.uits.musicplayer.model.AlbumModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

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
            val id = currentTrack.id
            val link = currentTrack.link
            val nameSong = currentTrack.nameSong
            val image = currentTrack.images
            val singer = currentTrack.nameSinger
            val time = currentTrack.time
            val recentListenings = RecentListenings()
            recentListenings.id = id
            recentListenings.title = nameSong
            recentListenings.name = singer
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
            val list = mutableListOf<Favorite>()
            val favoriteRepository: FavoriteRepository = FavoriteRepository(
                application
            )
            val favorite = Favorite()
            favoriteRepository.get().observeForever {
                list.clear()
                list.addAll(it)
                for (i in list) {
                    if (i.id == id) {
                        ibtnFavorite.visibility = INVISIBLE
                        ibtnUnFavorite.visibility = VISIBLE
                    } else {
                        ibtnFavorite.visibility = VISIBLE
                        ibtnUnFavorite.visibility = INVISIBLE
                    }
                }
            }
            ibtnFavorite.setOnClickListener(View.OnClickListener {
                fun insert(favorite: Favorite) =
                    MainScope().launch(Dispatchers.IO) {
                        favoriteRepository.insert(favorite)
                    }


                favorite.id = id
                favorite.images = image
                favorite.time = time
                favorite.singer = singer
                favorite.title = nameSong
                favorite.link = link
                insert(favorite)

                ibtnFavorite.visibility = INVISIBLE
                ibtnUnFavorite.visibility = VISIBLE

            })
            ibtnUnFavorite.setOnClickListener(View.OnClickListener {
                fun deleteId(id: String) =
                    MainScope().launch(Dispatchers.IO) {
                        favoriteRepository.deleteId(id)
                    }
                deleteId(id)
                ibtnUnFavorite.visibility = INVISIBLE
                ibtnFavorite.visibility = VISIBLE

            })
            ibtnNextSongPlayer.setOnClickListener(View.OnClickListener {
                next++

                if (next > musicList.size-1) {
                    next = musicList.size -1
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
        val intent1 = Intent(MainActivity.BROADCAST_DEFAULT_ALBUM_CHANGED)
        intent1.putExtra("playing_music1", musicList?.let { ArrayList(it) })
        intent1.putExtra("position",next)
        intent1.putExtra("isPlay",true)
        LocalBroadcastManager.getInstance(application).sendBroadcast(intent1)

    }
    fun playMusic1(
        musicList: MutableList<AlbumModel>,
        currentTrackIndex: Int,

    ) {
        var next = currentTrackIndex

        if (next < musicList.size) {
            var currentTrack = musicList[next]
            mediaPlayer.reset()
            mediaPlayer.setDataSource(currentTrack.link)
            mediaPlayer.prepare()
            mediaPlayer.start()
            // Nghe sự kiện kết thúc bài hát
            mediaPlayer.setOnCompletionListener {
                if (next >= musicList.size) {
                    next = 0
                    playMusic1(
                        musicList,
                        next
                    )

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
    fun startMusic(): Boolean {
        if (mediaPlayer.isPlaying) {
            return true
        }
        return false
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
