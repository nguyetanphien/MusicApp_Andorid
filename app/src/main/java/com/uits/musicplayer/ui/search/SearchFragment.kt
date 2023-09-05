package com.uits.musicplayer.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uits.musicplayer.database.entities.RecentHistory
import com.uits.musicplayer.databinding.FragmentDashboardBinding
import com.uits.musicplayer.interfaces.OnItemClickListener
import com.uits.musicplayer.model.SearchModel
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import java.util.Random
import kotlin.math.log


class SearchFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    lateinit var mSearchAdapterMusic: AdapterMusic
    lateinit var adapterRecentAdapter: RecentAdapter
    lateinit var dashboardViewModel: SearchViewModel
    lateinit var recentHistoryViewModel: RecentHistoryViewModel
    lateinit var root: View

    private var mListData: MutableList<SearchModel> = mutableListOf()
    private var mListDataRecent: MutableList<RecentHistory> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Tạo ViewModel và kết nối với Fragment

        dashboardViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        root = binding.root
        val edtSearch: AutoCompleteTextView = binding.edtsearchsong
        val rlRecentSearch: RelativeLayout = binding.rlRecentSearch
        val mRecyclerView: RecyclerView = binding.mRecycleView
        edtSearch.setOnClickListener(View.OnClickListener {
            rlRecentSearch.visibility = View.VISIBLE
            mRecyclerView.visibility = View.INVISIBLE

        })
        edtSearch.doOnTextChanged { text, start, before, count ->
            Log.d("ppppppppppp", text.toString())
        }

        rvListSearch()
        rvRecentSearch()
        clear()
        return root
    }

    private fun rvListSearch() {


        val mRecyclerView: RecyclerView = binding.mRecycleView
        mRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mSearchAdapterMusic =
            AdapterMusic(requireActivity(), mListData, object : OnItemClickListener {
                override fun onItemClick(position: Int) {

                }
            })
        mRecyclerView.adapter = ScaleInAnimationAdapter(mSearchAdapterMusic)

        dashboardViewModel.listDataAlbum.observe(viewLifecycleOwner) {
            mListData.clear()
            mListData.addAll(it)
            mSearchAdapterMusic.notifyDataSetChanged()

        }

    }

    private fun rvRecentSearch() {

        recentHistoryViewModel = ViewModelProvider(
            this, RecentHistoryViewModel.RecentViewModelFactory(this.requireActivity())
        ).get(RecentHistoryViewModel::class.java)
        root = binding.root
        val mRecyclerView: RecyclerView = binding.rvRecentSearch
        mRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapterRecentAdapter =
            RecentAdapter(requireActivity(), mListDataRecent, object : OnItemClickListener {
                override fun onItemClick(position: Int) {
                }

            })

        mRecyclerView.adapter = ScaleInAnimationAdapter(adapterRecentAdapter)
        recentHistoryViewModel.getDAta().observe(viewLifecycleOwner) {
            mListDataRecent.clear()
            mListDataRecent.addAll(it)
            mSearchAdapterMusic.notifyDataSetChanged()

        }
        //var i=0
//        for (i :Int  in 1 ..10 ){
//
//            val recentHistory = RecentHistory()
//        recentHistory.id= i.toString()
//        recentHistory.images =
//            "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0"
//        recentHistory.name = "1234"
//        recentHistory.title = "nhac"
//        recentHistoryViewModel.insert(recentHistory)
//        }


    }
    fun clear(){
        val ibt: AppCompatTextView = binding.txtClearRecentSearch
        ibt.setOnClickListener(View.OnClickListener {
            recentHistoryViewModel.delete()
            adapterRecentAdapter.notifyDataSetChanged()
        })
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dashboardViewModel.fetchDataAlbum()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}