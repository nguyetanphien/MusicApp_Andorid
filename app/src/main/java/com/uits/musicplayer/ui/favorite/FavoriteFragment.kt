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
        mRvPlaylits()
        mRVAlbumsL()

        nextFavoriteTrack()
        return root
    }

    private fun mRvPlaylits() {
        var mRecyclerViewPL: RecyclerView = binding.mRVPlaylistsL
        mRecyclerViewPL.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        adrapterFavoriteAdapterPL =
            FavoriteAdapterPL(requireActivity(), mListPlayerList, object : OnItemClickListener {
                override fun onItemClick(
                    position: Int,
                    id: String,
                    button: ImageButton,
                    link: String,
                    title: String,
                    singer: String,
                    images: String
                ) {
                    TODO("Not yet implemented")
                }

                override fun onItemClick2(
                    position: Int,
                    id: String,
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
            mListPlayerList.clear()
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
                override fun onItemClick(
                    position: Int,
                    id: String,
                    button: ImageButton,
                    link: String,
                    title: String,
                    singer: String,
                    images: String
                ) {

                }

                override fun onItemClick2(
                    position: Int,
                    id: String,
                    link: String,
                    title: String,
                    singer: String,
                    images: String
                ) {
                    val intent = Intent(context, AlbumActivity::class.java)
                    var nameAlbum = mListPlayerListAL[position].nameAlbum
                    intent.putExtra("album", nameAlbum)
                    startActivityForResult(intent, 1)
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}