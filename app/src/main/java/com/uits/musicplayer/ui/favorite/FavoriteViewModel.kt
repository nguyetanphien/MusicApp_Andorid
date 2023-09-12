package com.uits.musicplayer.ui.favorite

import android.app.Application
import android.content.res.AssetManager
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uits.musicplayer.model.HomeModel
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
    private val mListLiveData = MutableLiveData<List<HomeModel>>().apply { }
    val listLive: LiveData<List<HomeModel>> = mListLiveData
    var mListDataAsset: MutableList<HomeModel> = mutableListOf()
    val context = getApplication<Application>().applicationContext
    var mAssets: AssetManager = context.assets
    private val _listLiveRL = MutableLiveData<List<HomeModel>>().apply { }
    val _listDataRL: LiveData<List<HomeModel>> = _listLiveRL
    fun fetchDataplayList() {
        var current = ""
        current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var formatter = DateTimeFormatter.ofPattern("yyyy")
            LocalDateTime.now().format(formatter)
        } else {
            val time = Calendar.getInstance().time
            var formatter = SimpleDateFormat("yyyy")
            formatter.format(time)
        }
        val list: MutableList<HomeModel> = mutableListOf()
        list.add(
            HomeModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                current
            )
        )
        list.add(
            HomeModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                current
            )
        )
        list.add(
            HomeModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                current
            )
        )
        list.add(
            HomeModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                current
            )
        )
        list.add(
            HomeModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                current
            )
        )
        list.add(
            HomeModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                current
            )
        )
        list.add(
            HomeModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                current
            )
        )
        list.add(
            HomeModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                current
            )
        )
        list.add(
            HomeModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                current
            )
        )
        list.add(
            HomeModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                current
            )
        )
        mListLiveData.postValue(list)
    }

    private val mListLiveDataAl = MutableLiveData<List<HomeModel>>().apply { }
    val listLiveAl: LiveData<List<HomeModel>> = mListLiveDataAl
    fun loadTopAlbumAsset() {
        var current = ""
        current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var formatter = DateTimeFormatter.ofPattern("yyyy")
            LocalDateTime.now().format(formatter)
        } else {
            val time = Calendar.getInstance().time
            var formatter = SimpleDateFormat("yyyy")
            formatter.format(time)
        }
        // val list: MutableList<HomeModel> = mutableListOf()
        mListDataAsset.clear()
        val soundName: Array<String>?
        var name: String
        var img: String
        var year: String

        try {
            soundName = mAssets.list("album")
            Log.i("ppp", "Found" + soundName!!.size + " sounds")
            // mListDataAsset.add(soundName)
            soundName.forEach {
                name = it
                img =
                    "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0"
                year = current
                mListDataAsset.add(HomeModel(img, name, year))
            }
            mListLiveDataAl.postValue(mListDataAsset)

        } catch (ioe: IOException) {
            Log.e("ppp", "Could not list assets", ioe)
            return
        }
    }

}