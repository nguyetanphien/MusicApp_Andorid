package com.uits.musicplayer.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.RelativeLayout
import androidx.core.view.isInvisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uits.musicplayer.databinding.FragmentDashboardBinding
import com.uits.musicplayer.interfaces.OnItemClickListener
import com.uits.musicplayer.model.ArtistModel
import com.uits.musicplayer.model.SearchModel
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter


class SearchFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    lateinit var mSearchAdapterMusic: AdapterMusic
    lateinit var adapterRecentAdapter: RecentAdapter
    lateinit var dashboardViewModel: SearchViewModel
    private var mListData: MutableList<SearchModel> = mutableListOf()
    private var mListDataRecent: MutableList<SearchModel> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Tạo ViewModel và kết nối với Fragment
        dashboardViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val edtSearch: AutoCompleteTextView = binding.edtsearchsong
        val rlRecentSearch :RelativeLayout =binding.rlRecentSearch
        val mRecyclerView: RecyclerView = binding.mRecycleView
        edtSearch.setOnClickListener(View.OnClickListener {
            rlRecentSearch.visibility=View.VISIBLE
            mRecyclerView.visibility=View.INVISIBLE
            rvRecentSearch()
        })
        edtSearch.doOnTextChanged { text, start, before, count ->
            Log.d("ppppppppppp", text.toString())
        }

        rvlistSearch()
        rvRecentSearch()

        return root
    }
    private fun rvlistSearch(){
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
     private fun rvRecentSearch(){

         val mRecyclerView: RecyclerView = binding.rvRecentSearch
         mRecyclerView.layoutManager =
             LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
         adapterRecentAdapter =
             RecentAdapter(requireActivity(), mListDataRecent, object : OnItemClickListener {
                 override fun onItemClick(position: Int) {

                 }

             })
         dashboardViewModel.fetchDataRecent()
         mRecyclerView.adapter = ScaleInAnimationAdapter(adapterRecentAdapter)

         dashboardViewModel.liVeData.observe(viewLifecycleOwner) {
             mListDataRecent.clear()
             mListDataRecent.addAll(it)
             mSearchAdapterMusic.notifyDataSetChanged()

         }


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