package com.uits.musicplayer.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.database.ktx.values
import com.google.firebase.ktx.Firebase
import com.uits.musicplayer.R
import com.uits.musicplayer.database.entities.Favorite
import com.uits.musicplayer.database.entities.RecentListenings
import com.uits.musicplayer.database.repository.FavoriteRepository
import com.uits.musicplayer.databinding.FragmentHomeBinding
import com.uits.musicplayer.interfaces.OnItemClickListener
import com.uits.musicplayer.model.AlbumModel
import com.uits.musicplayer.model.HomeModel
import com.uits.musicplayer.ui.player.PlayerActivity
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.Random


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var apdapterHomeAdapter: HomeAdapter
    private lateinit var apdapterHomeAdapterTA: HomeAdapterTopAlbum
    private lateinit var apdapterHomeAdapterRL: HomeAdapterRL
    private val listR: MutableList<HomeModel> = mutableListOf()
    private val listRL: MutableList<RecentListenings> = mutableListOf()
    private val listTA: MutableList<HomeModel> = mutableListOf()
    val listId = mutableListOf<String>()
    private lateinit var homeViewModel: HomeViewModel
    private var mFirebaseDatabaseReference: DatabaseReference? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        mRVRecentListenings()
        mRVRecommendations()
        mRVTopAlbum()
        //    initFireRealData()
        return root
    }


    private fun mRVRecommendations() {
        val listR2: MutableList<RecentListenings> = mutableListOf()
        var listString = mutableListOf<String>()
        val mRecyclerView: RecyclerView = binding.mRecommendations
        mRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        apdapterHomeAdapter = HomeAdapter(requireActivity(), listR, object : OnItemClickListener {

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

        mRecyclerView.adapter = ScaleInAnimationAdapter(apdapterHomeAdapter)
        homeViewModel._liveData.observe(viewLifecycleOwner) {
            listR.clear()
            listR.addAll(it)
            apdapterHomeAdapter.notifyDataSetChanged()

        }
        homeViewModel.getDAta().observe(viewLifecycleOwner) {
            listR2.clear()
            listR2.addAll(it)
            for (i in listR2) {
                listString.add(i.id)

            }
        }

    }

    private fun mRVRecentListenings() {
        var list1 = mutableListOf<Favorite>()
        val mRecyclerViewRL: RecyclerView = binding.mRecentListening
        mRecyclerViewRL.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        apdapterHomeAdapterRL =
            HomeAdapterRL(requireActivity(), listRL, object : OnItemClickListener {
                override fun onItemClick(
                    position: Int,
                    id: String,
                    button: ImageButton,
                    link: String,
                    title: String,
                    singer: String,
                    images: String
                ) {
                    val popupMenu = PopupMenu(requireContext(), button)
                    val favorite = Favorite()
                    popupMenu.inflate(R.menu.menu)
                    popupMenu.menu.findItem(R.id.itemDeleteRL).isVisible = true
                    for (i in list1) {
                        if (id == i.id) {
                            popupMenu.menu.findItem(R.id.itemUnFavorite).isVisible = true
                            popupMenu.menu.findItem(R.id.itemFavorite).isVisible = false
                            break
                        }
                    }

                    popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
                        if (menuItem.itemId == R.id.itemFavorite) {
                            favorite.id = id
                            favorite.images = images
                            favorite.time = homeViewModel.duration(link)
                            favorite.singer = singer
                            favorite.title = title
                            favorite.link = link
                            homeViewModel.insertF(favorite)
                            true
                        } else if (menuItem.itemId == R.id.itemDeleteRL) {
                            homeViewModel.deleteid(id)
                        } else {
                            homeViewModel.deleteIdF(id)
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
                    val intent = Intent(requireContext(), PlayerActivity::class.java)
                    val list = mutableListOf<AlbumModel>()
                    val time = homeViewModel.duration(link)
                    list.add(AlbumModel(id, title, singer, link, time, images))
                    intent.putParcelableArrayListExtra("listMusic", ArrayList(list))
                    startActivity(intent)
                }

            })

        mRecyclerViewRL.adapter = ScaleInAnimationAdapter(apdapterHomeAdapterRL)
        homeViewModel.getDAta().observe(viewLifecycleOwner) { it ->
            listRL.clear()
            listRL.addAll(it)
            listRL.forEach {
                listId.add(it.id)
            }
            homeViewModel.fetchDataAlbum(listId)
            listRL.reverse()
            apdapterHomeAdapterRL.notifyDataSetChanged()
        }
        homeViewModel.getFavorit().observe(viewLifecycleOwner) {
            list1.clear()
            list1.addAll(it)
        }
    }

    private fun mRVTopAlbum() {
        val mRecyclerViewRL: RecyclerView = binding.mTopAlbums
        mRecyclerViewRL.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        apdapterHomeAdapterTA =
            HomeAdapterTopAlbum(requireActivity(), listTA, object : OnItemClickListener {
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
        mRecyclerViewRL.adapter = ScaleInAnimationAdapter(apdapterHomeAdapterTA)
        homeViewModel._listDataTA.observe(viewLifecycleOwner) {
            listTA.clear()
            listTA.addAll(it)
            apdapterHomeAdapterTA.notifyDataSetChanged()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.featchData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}






