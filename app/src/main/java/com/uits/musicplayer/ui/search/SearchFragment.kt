package com.uits.musicplayer.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uits.musicplayer.database.entities.RecentHistory
import com.uits.musicplayer.databinding.FragmentDashboardBinding
import com.uits.musicplayer.interfaces.OnItemClickListener
import com.uits.musicplayer.model.AlbumModel
import com.uits.musicplayer.model.SearchModel
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlin.random.Random
import kotlin.random.nextInt


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
        val edtSearch: AppCompatEditText = binding.edtsearchsong
        val rlRecentSearch: RelativeLayout = binding.rlRecentSearch
        val mRecyclerView: RecyclerView = binding.mRecycleView
//        edtSearch.doOnTextChanged { text, start, before, count ->
//            Log.d("ppppppppppp", text.toString())
//        }


        rvListSearch()
        rvRecentSearch()
        edtSearch.onFocusChangeListener = View.OnFocusChangeListener { p0, p1 ->
            rlRecentSearch.visibility = View.VISIBLE
            mRecyclerView.visibility = View.INVISIBLE
        }
//                edtSearch.setOnClickListener( View.OnClickListener {
//            rlRecentSearch.visibility = View.VISIBLE
//            mRecyclerView.visibility = View.INVISIBLE
//        })
        clear()

        return root
    }

    private fun rvListSearch() {
        val mRecyclerView: RecyclerView = binding.mRecycleView
        mRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mSearchAdapterMusic =
            AdapterMusic(requireActivity(), mListData, object : OnItemClickListener {
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
        )[RecentHistoryViewModel::class.java]
        root = binding.root
        val mRecyclerView: RecyclerView = binding.rvRecentSearch
        mRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapterRecentAdapter =
            RecentAdapter(requireActivity(), mListDataRecent, object : OnItemClickListener {
                override fun onItemClick(position: Int, id: String) {
                    recentHistoryViewModel.deleteid(id)
                    adapterRecentAdapter.notifyDataSetChanged()
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
        val id = Random.nextInt()
        val image =
            "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0"
        val title = "My Song"
        val name = "ntp"
        var recentHistory = RecentHistory()
        recentHistory.id = id.toString()
        recentHistory.images = image
        recentHistory.title = title
        recentHistory.name = name
        // recentHistoryViewModel.insert(recentHistory)

        mRecyclerView.adapter = ScaleInAnimationAdapter(adapterRecentAdapter)
        recentHistoryViewModel.getDAta().observe(viewLifecycleOwner) {
            mListDataRecent.clear()
            mListDataRecent.addAll(it)
            adapterRecentAdapter.notifyDataSetChanged()
        }
    }

    private fun clear() {
        val ibt: AppCompatTextView = binding.txtClearRecentSearch
        ibt.setOnClickListener(View.OnClickListener {
            recentHistoryViewModel.delete()
            adapterRecentAdapter.notifyDataSetChanged()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dashboardViewModel.featchData()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}