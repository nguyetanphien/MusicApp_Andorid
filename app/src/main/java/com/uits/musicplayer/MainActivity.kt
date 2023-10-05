package com.uits.musicplayer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.uits.musicplayer.databinding.ActivityMainBinding
import com.uits.musicplayer.model.AlbumModel
import com.uits.musicplayer.ui.player.MediaPlayerManager.isMusicPlaying
import com.uits.musicplayer.ui.player.MediaPlayerManager.pauseMusic
import com.uits.musicplayer.ui.player.MediaPlayerManager.playMusic1
import com.uits.musicplayer.ui.player.MediaPlayerManager.resumeMusic
import com.uits.musicplayer.ui.player.MediaPlayerManager.startMusic


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    companion object {
        const val BROADCAST_DEFAULT_ALBUM_CHANGED = "playing_music"
    }

    private val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {
            if (intent != null) {
                handleAlbumChanged(intent)

            }
        }
    }
    private val list2= mutableListOf<AlbumModel>()
    var p=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    fun handleAlbumChanged(intent: Intent) {

        val rlAlbum1 = binding.rlAlbum1
        val list =
            intent.getParcelableArrayListExtra<AlbumModel>("playing_music1")
        val position = intent.getIntExtra("position", 0)
        val isPlay = intent.getBooleanExtra("isPlay", false)
        val intent1=Intent(BROADCAST_DEFAULT_ALBUM_CHANGED)
        if (list != null) {
            list2.clear()
            list2.addAll(list)
        }
        p=position
        val imgSongAlbum = binding.imgSongAlbum
        val txtNameSongAlbum = binding.txtNameSongAlbum
        val txtNameSingerAlbum = binding.txtNameSingerAlbum
        val ibtnPauseSongAlbum = binding.ibtnPauseSongAlbum
        val ibtnPlaySongAlbum = binding.ibtnPlaySongAlbum
        val ibtnNextSongAlbum = binding.ibtnNextSongAlbum
        val ibtnBackSongAlbum = binding.ibtnBackSongAlbum
        if (startMusic()) {
            rlAlbum1.visibility = View.VISIBLE
        }
        if (isPlay) {
            ibtnPauseSongAlbum.visibility = View.VISIBLE
            ibtnPlaySongAlbum.visibility = View.INVISIBLE
        } else {
            ibtnPauseSongAlbum.visibility = View.INVISIBLE
            ibtnPlaySongAlbum.visibility = View.VISIBLE
        }
        rlAlbum1.setOnClickListener(View.OnClickListener { })

        if (list != null) {
            if (position < list.size) {
                val currentSong = list[position]
                val link = currentSong.link
                val nameSong = currentSong.nameSong
                val image = currentSong.images
                val singer = currentSong.nameSinger

                Glide.with(application).load(image).centerCrop()
                    .placeholder(R.mipmap.ic_launcher).into(imgSongAlbum)
                val userSong: String = if (nameSong.length >= 5) {
                    nameSong.substring(0, 5) + "..."
                } else {
                    nameSong
                }

                txtNameSongAlbum.text = userSong
                val userName: String = if (singer.length >= 5) {
                    singer.substring(0, 5) + "..."
                } else {
                    singer
                }
                txtNameSingerAlbum.text = userName
            }
        }
        ibtnPauseSongAlbum.setOnClickListener(View.OnClickListener {
            ibtnPauseSongAlbum.visibility = View.INVISIBLE
            ibtnPlaySongAlbum.visibility = View.VISIBLE
            pauseMusic()
            intent1.putExtra("isPlay",false)
            LocalBroadcastManager.getInstance(application).sendBroadcast(intent1)

        })
        ibtnPlaySongAlbum.setOnClickListener(View.OnClickListener {
            ibtnPauseSongAlbum.visibility = View.VISIBLE
            ibtnPlaySongAlbum.visibility = View.INVISIBLE
            resumeMusic()
            intent1.putExtra("isPlay",true)
            LocalBroadcastManager.getInstance(application).sendBroadcast(intent1)
        })
        ibtnNextSongAlbum.setOnClickListener(View.OnClickListener {

            p=position+1
            if(p<list2.size){
                playMusic1(list2,p)
                intent1.putParcelableArrayListExtra("playing_music1",ArrayList(list2))
                intent1.putExtra("position",p)
                intent1.putExtra("isPlay",true)
                LocalBroadcastManager.getInstance(application).sendBroadcast(intent1)
            }

        })
        ibtnBackSongAlbum.setOnClickListener(View.OnClickListener {

            p=position-1
            if(p>=0){
                playMusic1(list2,p)
                intent1.putParcelableArrayListExtra("playing_music1",ArrayList(list2))
                intent1.putExtra("position",p)
                intent1.putExtra("isPlay",true)
                LocalBroadcastManager.getInstance(application).sendBroadcast(intent1)
            }


        })
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(broadCastReceiver, IntentFilter(BROADCAST_DEFAULT_ALBUM_CHANGED))
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(broadCastReceiver)
    }

}