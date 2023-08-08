package com.uits.musicplayer.ui.player.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uits.musicplayer.databinding.FragmentHomeBinding
import com.uits.musicplayer.interfaces.OnItemClickListener
import com.uits.musicplayer.model.HomeModel
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var apdapterHomeAdapter: HomeAdapter
    private lateinit var apdapterHomeAdapterTA: HomeAdapterTopAlbum
    private lateinit var apdapterHomeAdapterRL: HomeAdapterRL
    private val listR: MutableList<HomeModel> = mutableListOf()
    private val listRL: MutableList<HomeModel> = mutableListOf()
    private val listTA: MutableList<HomeModel> = mutableListOf()
    private lateinit var homeViewModel: HomeViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        mRVRecommendations()
        mRVRecentListenings()
        mRVTopAlbum()
        return root
    }

    private fun mRVRecommendations() {
        val mRecyclerView: RecyclerView = binding.mRecommendations
        mRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        apdapterHomeAdapter = HomeAdapter(requireActivity(), listR, object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                TODO("Not yet implemented")
            }

        })
        mRecyclerView.adapter = ScaleInAnimationAdapter(apdapterHomeAdapter)
        homeViewModel._liveData.observe(viewLifecycleOwner) {
            listR.addAll(it)
            apdapterHomeAdapter.notifyDataSetChanged()

        }
    }

    private fun mRVRecentListenings() {
        val mRecyclerViewRL: RecyclerView = binding.mRecentListening
        mRecyclerViewRL.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        apdapterHomeAdapterRL =
            HomeAdapterRL(requireActivity(), listRL, object : OnItemClickListener {
                override fun onItemClick(position: Int) {
                    TODO("Not yet implemented")
                }

            })
        mRecyclerViewRL.adapter = ScaleInAnimationAdapter(apdapterHomeAdapterRL)
        homeViewModel._listDataRL.observe(viewLifecycleOwner) {
            listRL.addAll(it)
            apdapterHomeAdapterRL.notifyDataSetChanged()
        }
    }

    private fun mRVTopAlbum() {
        val mRecyclerViewRL: RecyclerView = binding.mTopAlbums
        mRecyclerViewRL.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        apdapterHomeAdapterTA =
            HomeAdapterTopAlbum(requireActivity(), listTA, object : OnItemClickListener {
                override fun onItemClick(position: Int) {
                    TODO("Not yet implemented")
                }

            })
        mRecyclerViewRL.adapter = ScaleInAnimationAdapter(apdapterHomeAdapterTA)
        homeViewModel._listDataTA.observe(viewLifecycleOwner) {
            listTA.addAll(it)
            apdapterHomeAdapterTA.notifyDataSetChanged()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.fetchDataAlbum()
        homeViewModel.fetchDataAlbumRL()
        homeViewModel.fetchDataAlbumTA()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}






