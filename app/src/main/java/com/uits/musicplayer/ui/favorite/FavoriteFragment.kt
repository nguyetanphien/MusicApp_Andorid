package com.uits.musicplayer.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uits.musicplayer.databinding.FragmentNotificationsBinding
import com.uits.musicplayer.interfaces.OnItemClickListener
import com.uits.musicplayer.model.HomeModel
import com.uits.musicplayer.ui.favorite.Track.TracksActivity
import com.uits.musicplayer.ui.player.home.HomeAdapter
import com.uits.musicplayer.ui.player.home.HomeAdapterTopAlbum
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

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
                override fun onItemClick(position: Int, id: String) {
                    TODO("Not yet implemented")
                }

                override fun onItemClick2(
                    position: Int,
                    link: String,
                    title: String,
                    singer: String
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
        mRecyclerViewAl.layoutManager = GridLayoutManager(activity, 2,GridLayoutManager.VERTICAL,false)

        adrapterFavoriteFragment =
            FavoriteAdapterAL(requireActivity(), mListPlayerListAL, object : OnItemClickListener {
                override fun onItemClick(position: Int, id: String) {
                }

                override fun onItemClick2(
                    position: Int,
                    link: String,
                    title: String,
                    singer: String
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
        notificationsViewModel.loadTopAlbumAsset()
    }
    private fun nextFavoriteTrack(){
        val itbnnext: ImageButton=binding.ibtnNextL
        itbnnext.setOnClickListener(View.OnClickListener {
            val intent= Intent(requireContext(),TracksActivity::class.java)
            startActivity(intent)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}