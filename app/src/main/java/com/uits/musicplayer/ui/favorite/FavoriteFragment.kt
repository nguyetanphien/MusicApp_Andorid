package com.uits.musicplayer.ui.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uits.musicplayer.R
import com.uits.musicplayer.databinding.FragmentNotificationsBinding
import com.uits.musicplayer.interfaces.OnItemClickListener
import com.uits.musicplayer.model.AlbumModel
import com.uits.musicplayer.model.HomeModel
import com.uits.musicplayer.service.APIClient
import com.uits.musicplayer.service.response.MusicResponse
import com.uits.musicplayer.ui.album.AlbumActivity
import com.uits.musicplayer.ui.favorite.Track.TracksActivity
import com.uits.musicplayer.ui.player.MediaPlayerManager
import com.uits.musicplayer.ui.home.HomeAdapter
import com.uits.musicplayer.ui.home.HomeAdapterTopAlbum
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import java.io.IOException

class FavoriteFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    lateinit var adrapterFavoriteAdapterPL: FavoriteAdapterPL
    lateinit var adrapterFavoriteFragment: FavoriteAdapterAL
    val mListPlayerList: MutableList<HomeModel> = mutableListOf()
    val mListPlayerListAL: MutableList<HomeModel> = mutableListOf()
    lateinit var notificationsViewModel: FavoriteViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        notificationsViewModel =
            ViewModelProvider(this).get(FavoriteViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val rlMusic: RelativeLayout = binding.rlMusic
        if (MediaPlayerManager.startMusic())
            rlMusic.visibility = View.VISIBLE
        mRvPlaylits()
        mRVAlbumsL()
        playAndPause()
        nextFavoriteTrack()
        return root
    }

    private fun mRvPlaylits() {
        var mRecyclerViewPL: RecyclerView = binding.mRVPlaylistsL
        mRecyclerViewPL.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        adrapterFavoriteAdapterPL =
            FavoriteAdapterPL(requireActivity(), mListPlayerList, object : OnItemClickListener {
                override fun onItemClick(position: Int, id: String) {
                    TODO("Not yet implemented")
                }

                override fun onItemClick2(
                    position: Int,
                    link: String,
                    title: String,
                    singer: String,
                    images: String
                ) {
                    TODO("Not yet implemented")
                }

            })
        mRecyclerViewPL.adapter = ScaleInAnimationAdapter(adrapterFavoriteAdapterPL)
        notificationsViewModel.listLive.observe(viewLifecycleOwner) {
            mListPlayerList.addAll(it)
            adrapterFavoriteAdapterPL.notifyDataSetChanged()
        }

    }

    private fun mRVAlbumsL() {
        var mRecyclerViewAl: RecyclerView = binding.mRVAlbumL
        mRecyclerViewAl.layoutManager =
            GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)

        adrapterFavoriteFragment =
            FavoriteAdapterAL(requireActivity(), mListPlayerListAL, object : OnItemClickListener {
                override fun onItemClick(position: Int, id: String) {
                    val intent = Intent(context, AlbumActivity::class.java)
                    var nameAlbum = mListPlayerListAL[position].nameAlbum
                    intent.putExtra("album", nameAlbum)
                    startActivityForResult(intent, 1)
                }

                override fun onItemClick2(
                    position: Int,
                    link: String,
                    title: String,
                    singer: String,
                    images: String
                ) {
                    TODO("Not yet implemented")
                }

            })
        mRecyclerViewAl.adapter = adrapterFavoriteFragment
        notificationsViewModel.listLiveAl.observe(viewLifecycleOwner) {
            mListPlayerListAL.clear()
            mListPlayerListAL.addAll(it)
            adrapterFavoriteFragment.notifyDataSetChanged()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notificationsViewModel.fetchDataplayList()
        notificationsViewModel.fetchDataAlbum()
    }

    private fun nextFavoriteTrack() {
        val itbnnext: ImageButton = binding.ibtnNextL
        itbnnext.setOnClickListener(View.OnClickListener {
            val intent = Intent(requireContext(), TracksActivity::class.java)
            startActivity(intent)
        })
    }

    private fun playAndPause() {
        val ibtnPauseSongAlbumAS: ImageButton = binding.ibtnPauseSong
        val ibtnPlaySongAlbumAS: ImageButton = binding.ibtnPlaySong
        if (MediaPlayerManager.startMusic()) {
            ibtnPauseSongAlbumAS.visibility = View.VISIBLE
            ibtnPlaySongAlbumAS.visibility = View.INVISIBLE
        } else {
            ibtnPauseSongAlbumAS.visibility = View.INVISIBLE
            ibtnPlaySongAlbumAS.visibility = View.VISIBLE
        }
        ibtnPauseSongAlbumAS.setOnClickListener(
            View.OnClickListener {
                MediaPlayerManager.pauseMusic()
                ibtnPauseSongAlbumAS.visibility = View.INVISIBLE
                ibtnPlaySongAlbumAS.visibility = View.VISIBLE
            }
        )
        ibtnPlaySongAlbumAS.setOnClickListener(
            View.OnClickListener {
                MediaPlayerManager.resumeMusic()
                ibtnPauseSongAlbumAS.visibility = View.VISIBLE
                ibtnPlaySongAlbumAS.visibility = View.INVISIBLE
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var play: String
        val imgSongAlbumAS: AppCompatImageView = binding.imgAlumL
        val txtNameSongAlbumAS: AppCompatTextView = binding.txtNameSongL
        val txtNameSingerAlbumAS: AppCompatTextView = binding.txtNameSingerL
        val rlAlbumAS: RelativeLayout = binding.rlMusic

        Log.d("ppp", requestCode.toString())
        Log.d("ppp", requestCode.toString())
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == 1) {
            play = data?.getStringExtra("play").toString()
            val nameSong = data?.getStringExtra("name")
            val nameSinger = data?.getStringExtra("singer")
            val image = data?.getStringExtra("image")

            Log.d("ppp", play)
            if (play == "ok") {
                rlAlbumAS.visibility = View.VISIBLE
                Glide.with(requireContext()).load(image).centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imgSongAlbumAS)
                txtNameSongAlbumAS.text = nameSong
                txtNameSingerAlbumAS.text = nameSinger


            } else {
                rlAlbumAS.visibility = View.INVISIBLE

            }
        }
    }


}