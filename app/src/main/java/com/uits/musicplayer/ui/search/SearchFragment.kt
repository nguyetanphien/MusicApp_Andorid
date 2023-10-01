package com.uits.musicplayer.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
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
import com.uits.musicplayer.ui.player.PlayerActivity
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter


class SearchFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    lateinit var mSearchAdapterMusic: AdapterMusic
    lateinit var adapterRecentAdapter: RecentAdapter
    lateinit var adapterSearchAdapter: SearchAdapter
    lateinit var dashboardViewModel: SearchViewModel
    private var mListData: MutableList<SearchModel> = mutableListOf()
    private var mListDataRecent: MutableList<RecentHistory> = mutableListOf()
    private var mListSearch: MutableList<AlbumModel> = mutableListOf()
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Tạo ViewModel và kết nối với Fragment
        dashboardViewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val edtSearch: AppCompatEditText = binding.edtsearchsong
        val rlRecentSearch: RelativeLayout = binding.rlRecentSearch
        val mRecyclerView: RecyclerView = binding.mRecycleView
        val rvSearch: RecyclerView = binding.rvSearch
        val root: View = binding.root

        rvRecentSearch()
        rvListSearch()
        rvSearch.visibility = View.INVISIBLE
        edtSearch.onFocusChangeListener = View.OnFocusChangeListener { p0, p1 ->
            rlRecentSearch.visibility = View.VISIBLE
            mRecyclerView.visibility = View.INVISIBLE

        }

        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                rvSearch.visibility = View.VISIBLE
                rlRecentSearch.visibility = View.INVISIBLE
                filter(s.toString())
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        clear()

        return root
    }

    private fun rvListSearch() {
        val mRecyclerView: RecyclerView = binding.mRecycleView
        mRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mSearchAdapterMusic =
            AdapterMusic(requireActivity(), mListData, object : OnItemClickListener {
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

        val mRecyclerView: RecyclerView = binding.rvRecentSearch
        mRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapterRecentAdapter =
            RecentAdapter(requireActivity(), mListDataRecent, object : OnItemClickListener {
                override fun onItemClick(
                    position: Int,
                    id: String,
                    button: ImageButton,
                    link: String,
                    title: String,
                    singer: String,
                    images: String
                ) {
                    dashboardViewModel.deleteid(id)
                    adapterRecentAdapter.notifyDataSetChanged()
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
                    list.add(AlbumModel(id, title, singer, link, "11", images))
                    intent.putParcelableArrayListExtra("listMusic", ArrayList(list))
                    startActivity(intent)
                }
            })

        mRecyclerView.adapter = ScaleInAnimationAdapter(adapterRecentAdapter)
        dashboardViewModel.getDAta().observe(viewLifecycleOwner) {
            mListDataRecent.clear()
            mListDataRecent.addAll(it)
            adapterRecentAdapter.notifyDataSetChanged()
        }
    }

    private fun filter(text: String) {
        val mRecyclerView: RecyclerView = binding.rvSearch
        val listSearch: MutableList<AlbumModel> = mutableListOf()
        mRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapterSearchAdapter =
            SearchAdapter(requireActivity(), listSearch, object : OnItemClickListener {
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
                    val recentHistory = RecentHistory()
                    recentHistory.id = id
                    recentHistory.link = link
                    recentHistory.title = title
                    recentHistory.name = singer
                    recentHistory.images = images
                    dashboardViewModel.insert(recentHistory)
                    val intent = Intent(context, PlayerActivity::class.java)
                    val list = mutableListOf<AlbumModel>()
                    list.add(listSearch[position])
                    intent.putParcelableArrayListExtra("listMusic", ArrayList(list))
                    context!!.startActivity(intent)
                }
            })
        mRecyclerView.adapter = ScaleInAnimationAdapter(adapterSearchAdapter)
        dashboardViewModel._liveData.observe(viewLifecycleOwner) {
            mListSearch.clear()
            mListSearch.addAll(it)
            Log.d("ppp", mListSearch.size.toString())
            adapterSearchAdapter.notifyDataSetChanged()

        }

        for (item in mListSearch) {
            if (item.nameSong.lowercase().contains(text.lowercase())) {
                listSearch.add(item)
                adapterSearchAdapter.notifyDataSetChanged()
            }
        }
        adapterSearchAdapter.notifyDataSetChanged()

    }

    private fun clear() {
        val ibt: AppCompatTextView = binding.txtClearRecentSearch
        ibt.setOnClickListener(View.OnClickListener {
            dashboardViewModel.delete()
            adapterRecentAdapter.notifyDataSetChanged()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dashboardViewModel.featchData()
        dashboardViewModel.fetchDataSearch()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}