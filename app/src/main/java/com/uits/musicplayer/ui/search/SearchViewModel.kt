package com.uits.musicplayer.ui.search


import android.annotation.SuppressLint
import android.app.Application
import android.content.res.AssetManager
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uits.musicplayer.model.AlbumModel
import com.uits.musicplayer.model.SearchModel
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class SearchViewModel(application: Application) : AndroidViewModel(application) {


    private val _listDataAlbum = MutableLiveData<List<SearchModel>>().apply {
    }
    val context = getApplication<Application>().applicationContext
    val listDataAlbum: LiveData<List<SearchModel>> = _listDataAlbum
    var mAssets: AssetManager = context.assets

    fun fetchDataAlbum() {
        var name = ""
        var listData: MutableList<SearchModel> = mutableListOf()
        val soundName: Array<String>?

        try {
            soundName = mAssets.list("album")
            Log.i("ppp", "Found" + soundName!!.size + " sounds")
            soundName.forEach {
                name = it
                listData.add(
                    SearchModel(
                        name,
                        "https://s3.amazonaws.com/thumbnails.venngage.com/template/ef47dc15-b2f6-4e7b-99b3-fa02d910c7dc.png"
                    )
                )
            }
            _listDataAlbum.postValue(
                listData
            )

        } catch (ioe: IOException) {
            Log.e("ppp", "Could not list assets", ioe)
            return
        }

    }

}