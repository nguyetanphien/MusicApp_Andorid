package com.uits.musicplayer.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import com.uits.musicplayer.database.entities.RecentListenings
import com.uits.musicplayer.databinding.FragmentHomeBinding
import com.uits.musicplayer.interfaces.OnItemClickListener
import com.uits.musicplayer.model.AlbumModel
import com.uits.musicplayer.model.HomeModel
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import java.util.Random


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var apdapterHomeAdapter: HomeAdapter
    private lateinit var apdapterHomeAdapterTA: HomeAdapterTopAlbum
    private lateinit var apdapterHomeAdapterRL: HomeAdapterRL
    private val listR: MutableList<HomeModel> = mutableListOf()
    private val listRL: MutableList<RecentListenings> = mutableListOf()
    private val listTA: MutableList<HomeModel> = mutableListOf()
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
        mRVRecommendations()
        mRVRecentListenings()
        mRVTopAlbum()
    //    initFireRealData()
        return root
    }

    private fun initFireRealData() {

        val database = Firebase.database
        val myRef = database.getReference("music")
        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {
                    Log.e("qqq", it.child("album").value.toString())
                }
                val v = dataSnapshot.child("1").child("album").value
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("qqq", "Failed to read value.", error.toException())
            }
        })
    }

    private fun mRVRecommendations() {
        val mRecyclerView: RecyclerView = binding.mRecommendations
        mRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        apdapterHomeAdapter = HomeAdapter(requireActivity(), listR, object : OnItemClickListener {

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
        mRecyclerView.adapter = ScaleInAnimationAdapter(apdapterHomeAdapter)
        homeViewModel._liveData.observe(viewLifecycleOwner) {
            listR.clear()
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
        val recentListenings=RecentListenings()
        val id= kotlin.random.Random.nextInt()
        val image="https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0"
        val title="My Song"

        recentListenings.id=id.toString()
        recentListenings.images=image
        recentListenings.time="3.20"
        recentListenings.link="https://storage.googleapis.com/uamp/The_Kyoto_Connection_-_Wake_Up/01_-_Intro_-_The_Way_Of_Waking_Up_feat_Alan_Watts.mp3"
        recentListenings.title=title

    //    homeViewModel.insert(recentListenings)
        mRecyclerViewRL.adapter = ScaleInAnimationAdapter(apdapterHomeAdapterRL)
        homeViewModel.getDAta().observe(viewLifecycleOwner) {
            listRL.clear()
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
        mRecyclerViewRL.adapter = ScaleInAnimationAdapter(apdapterHomeAdapterTA)
        homeViewModel._listDataTA.observe(viewLifecycleOwner) {
            listTA.clear()
            listTA.addAll(it)
            apdapterHomeAdapterTA.notifyDataSetChanged()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.fetchDataAlbum()
        homeViewModel.fetchDataAlbumRL()
        homeViewModel.featchData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}






