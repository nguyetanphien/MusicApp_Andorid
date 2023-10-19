package com.uits.musicplayer.ui.album


import android.R.attr.data
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uits.musicplayer.MainActivity
import com.uits.musicplayer.R
import com.uits.musicplayer.database.entities.Favorite
import com.uits.musicplayer.databinding.ActivityAlbumBinding
import com.uits.musicplayer.interfaces.OnItemClickListener
import com.uits.musicplayer.model.AlbumModel
import com.uits.musicplayer.ui.player.MediaPlayerManager
import com.uits.musicplayer.ui.player.MediaPlayerManager.pauseMusic
import com.uits.musicplayer.ui.player.MediaPlayerManager.resumeMusic
import com.uits.musicplayer.ui.player.PlayerActivity
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import java.io.IOException


class AlbumActivity : AppCompatActivity() {
    lateinit var binding: ActivityAlbumBinding
    private lateinit var viewModel: AlbumViewModel
    private lateinit var abbumAdapter: AlbumAdapter
    var mutableList: MutableList<AlbumModel> = mutableListOf()
    val listF = mutableListOf<Favorite>()
    val list2 = mutableListOf<AlbumModel>()
    var p = 0
    private val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {
            if (intent != null) {
                val rlAlbumAS: RelativeLayout = binding.rlAlbum1AS
                rlAlbumAS.visibility = View.VISIBLE
                playAndPause(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[AlbumViewModel::class.java]
        val rlAlbumAS: RelativeLayout = binding.rlAlbum1AS
        rVAlbumList()
        rlAlbumAS.setOnClickListener(View.OnClickListener { })
        back()
    }

    @SuppressLint("SetTextI18n")
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
            override fun onItemClick(
                position: Int,
                id: String,
                button: ImageButton,
                link: String,
                title: String,
                singer: String,
                images: String
            ) {
                val popupMenu = PopupMenu(applicationContext, button)
                popupMenu.inflate(R.menu.menu)
                for (i in listF) {
                    if (id == i.id) {
                        popupMenu.menu.findItem(R.id.itemUnFavorite).isVisible = true
                        popupMenu.menu.findItem(R.id.itemFavorite).isVisible = false
                        break
                    }
                }
                popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->

                    if (menuItem.itemId == R.id.itemFavorite) {

                        val favorite = Favorite()
                        favorite.id = id
                        favorite.images = images
                        favorite.time = viewModel.duration(link)
                        favorite.singer = singer
                        favorite.title = title
                        favorite.link = link
                        viewModel.insertF(favorite)
                        true
                    } else {
                        viewModel.deleteIdF(id)
                    }
                    false
                }
                popupMenu.show()
            }

            override fun onItemClick2(
                position: Int,
                id: String,
                link: String,
                title: String,
                singer: String,
                images: String
            ) {
                val intent = Intent(application, PlayerActivity::class.java)
                intent.putParcelableArrayListExtra("listMusic", ArrayList(mutableList))
                Log.d("ppp", position.toString())
                intent.putExtra("position", position)
                startActivity(intent)

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
                    ArrayList(mutableList)
                )
                txtAlbumTimeAS.text = mutableList.size.toString() + " " + getString(R.string.song)
            } catch (e: IOException) {
            }
        })
        viewModel.getFavorit().observe(this, Observer {
            listF.clear()
            listF.addAll(it)
        })


    }


    fun playAndPause(intent: Intent) {
        val intent1 = Intent(MainActivity.BROADCAST_DEFAULT_ALBUM_CHANGED)
        val list = intent.getParcelableArrayListExtra<AlbumModel>("playing_music1")
        val position = intent.getIntExtra("position", 0)
        val isPlay = intent.getBooleanExtra("isPlay", false)
        val ibtnPauseSongAlbumAS: ImageButton = findViewById(R.id.ibtnPauseSongAlbumAS)
        val ibtnPlaySongAlbumAS: ImageButton = findViewById(R.id.ibtnPlaySongAlbumAS)
        val imgSongAlbumAS: ImageView = findViewById(R.id.imgSongAlbumAS)
        val txtNameSongAlbumAS: TextView = findViewById(R.id.txtNameSongAlbumAS)
        val txtNameSingerAlbumAS: TextView = findViewById(R.id.txtNameSingerAlbumAS)
        val ibtnNextSongAlbumAS: ImageButton = findViewById(R.id.ibtnNextSongAlbumAS)
        val ibtnBackSongAlbumAS: ImageButton = findViewById(R.id.ibtnBackSongAlbumAS)

        if (list != null) {
            list2.clear()
            list2.addAll(list)
        }
        p = position
        if (list != null && position < list.size) {
            val currentSong = list[position]
            val nameSong = currentSong.nameSong
            val image = currentSong.images
            val singer = currentSong.nameSinger
            Glide.with(application).load(image).centerCrop()
                .placeholder(R.mipmap.ic_launcher).into(imgSongAlbumAS)
            val userSong: String = if (nameSong.length >= 5) {
                nameSong.substring(0, 5) + "..."
            } else {
                nameSong
            }

            txtNameSongAlbumAS.text = userSong
            val userName: String = if (singer.length >= 5) {
                singer.substring(0, 5) + "..."
            } else {
                singer
            }
            txtNameSingerAlbumAS.text = userName
        }

        if (isPlay) {
            ibtnPauseSongAlbumAS.visibility = View.VISIBLE
            ibtnPlaySongAlbumAS.visibility = View.INVISIBLE
        } else {
            ibtnPauseSongAlbumAS.visibility = View.INVISIBLE
            ibtnPlaySongAlbumAS.visibility = View.VISIBLE
        }

        ibtnPauseSongAlbumAS.setOnClickListener(View.OnClickListener {
            pauseMusic()
            ibtnPauseSongAlbumAS.visibility = View.INVISIBLE
            ibtnPlaySongAlbumAS.visibility = View.VISIBLE
            intent1.putExtra("isPlay", false)
            LocalBroadcastManager.getInstance(application).sendBroadcast(intent1)
        })
        ibtnPlaySongAlbumAS.setOnClickListener(View.OnClickListener {
            resumeMusic()
            ibtnPauseSongAlbumAS.visibility = View.VISIBLE
            ibtnPlaySongAlbumAS.visibility = View.INVISIBLE
            intent1.putExtra("isPlay", true)
            LocalBroadcastManager.getInstance(application).sendBroadcast(intent1)
        })

        ibtnNextSongAlbumAS.setOnClickListener(View.OnClickListener {
            p = position + 1
            if (p < (list2.size)) {
                MediaPlayerManager.playMusic1(list2, p)
                intent1.putParcelableArrayListExtra(
                    "playing_music1",
                    ArrayList(list2)
                )
                intent1.putExtra("position", p)
                intent1.putExtra("isPlay", true)
                LocalBroadcastManager.getInstance(application).sendBroadcast(intent1)
            }

        })
        ibtnBackSongAlbumAS.setOnClickListener(View.OnClickListener {

            p = position - 1
            if (p >= 0) {
                MediaPlayerManager.playMusic1(list2, p)
                intent1.putParcelableArrayListExtra("playing_music1", ArrayList(list2))
                intent1.putExtra("position", p)
                intent1.putExtra("isPlay", true)
                LocalBroadcastManager.getInstance(application).sendBroadcast(intent1)
            }


        })
    }


    private fun back() {
        val back: ImageButton = findViewById(R.id.ibtnBackAlbumAS)

        back.setOnClickListener(View.OnClickListener {

            finish()
        })
    }

    private fun playMusic(
        list: ArrayList<AlbumModel>
    ) {
        val imagePlay: ImageButton = findViewById(R.id.ibtnPlayAS)

        imagePlay.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, PlayerActivity::class.java)
            intent.putParcelableArrayListExtra("listMusic", list)
            startActivityForResult(intent, 1)
        })
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(
                broadCastReceiver,
                IntentFilter(MainActivity.BROADCAST_DEFAULT_ALBUM_CHANGED)
            )
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(broadCastReceiver)
    }
}




