package com.uits.musicplayer.ui.search.artist

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uits.musicplayer.R
import com.uits.musicplayer.databinding.ActivityArtistBinding
import com.uits.musicplayer.interfaces.OnItemClickListener
import com.uits.musicplayer.model.ArtistModel
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

class ArtistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArtistBinding
    private lateinit var viewModel: ArtistViewModel
    lateinit var artistAdapter: ArtistAdapter
    lateinit var artistAdapterPT: ArtistAdapterPT
    var mListData: MutableList<ArtistModel> = mutableListOf()
    var mListDataPT: MutableList<ArtistModel> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   supportActionBar?.hide()
        binding = ActivityArtistBinding.inflate(layoutInflater)

        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(ArtistViewModel::class.java)
        backGroug()
        rVAlbum()
        rVAlbumPT()
        back()

    }

    fun backGroug() {
        var backGroud: ImageView = findViewById(R.id.imgbackgroud)
        var nameSinger: TextView = findViewById(R.id.txtNameSinger)
        var txtBody: TextView = findViewById(R.id.txtBody)
        backGroud.setImageResource(R.mipmap.rv)
        nameSinger.text = "Huynh Cong Hieu"
        txtBody.text =
            "Rap Việt Mùa 3 đang phát sóng, những thí sinh xuất sắc được tuyển chọn khắp Việt Nam sẽ quy tụ tại sân khấu Rap Việt Mùa 3 để tranh tài nhằm tìm ra người nắm giữ ngôi vị cao nhất mùa thứ 3."

    }

    fun rVAlbum() {
        val mRecyclerView: RecyclerView = binding.mRecycleViewAlbum
        artistAdapter = ArtistAdapter(this, mListData, object : OnItemClickListener {
            override fun onItemClick(position: Int) {

            }

        })
        viewModel.fetchDataAlbum()
        mRecyclerView.adapter = ScaleInAnimationAdapter(artistAdapter)
        mRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        viewModel._liveData.observe(this, Observer { data ->
            mListData.clear()
            mListData.addAll(data)
            artistAdapter.notifyDataSetChanged()
        })
    }

    fun rVAlbumPT() {
        val mRecyclerViewPT = binding.mRecyclePopularTracks
        artistAdapterPT = ArtistAdapterPT(this, mListDataPT, object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                TODO("Not yet implemented")
            }

        })
        viewModel.fetchDataAlbumPT()
        mRecyclerViewPT.adapter = ScaleInAnimationAdapter(artistAdapterPT)
        mRecyclerViewPT.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewModel._listDataPT.observe(this, Observer { data ->
            mListDataPT.clear()
            mListDataPT.addAll(data)
            artistAdapterPT.notifyDataSetChanged()
        })
    }

    private fun back() {
        val imgbtnBack = binding.imgbtnBack
        imgbtnBack.setOnClickListener { finish() }
    }

}