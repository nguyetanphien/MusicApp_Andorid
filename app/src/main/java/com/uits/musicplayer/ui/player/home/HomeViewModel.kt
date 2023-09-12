package com.uits.musicplayer.ui.player.home

import android.app.Application
import android.content.res.AssetManager
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uits.musicplayer.model.AlbumModel
import com.uits.musicplayer.model.HomeModel
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _list = MutableLiveData<List<HomeModel>>().apply {

    }
    var mListDataAsset: MutableList<HomeModel> = mutableListOf()
    val context = getApplication<Application>().applicationContext
    var mAssets: AssetManager = context.assets
    val _liveData: LiveData<List<HomeModel>> = _list
    private val _listLiveRL = MutableLiveData<List<HomeModel>>().apply { }
    val _listDataRL: LiveData<List<HomeModel>> = _listLiveRL
    fun fetchDataAlbum() {
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
        _list.postValue(list)
    }


    fun fetchDataAlbumRL() {
        var current = ""
        current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            LocalDateTime.now().format(formatter)
        } else {
            val time = Calendar.getInstance().time
            var formatter = SimpleDateFormat("dd-MM-yyyy")
            formatter.format(time)
        }
        var list: MutableList<HomeModel> = mutableListOf()
        list.add(
            HomeModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My love",
                current,
                "6:30"
            )
        )
        list.add(
            HomeModel(
                "https://th.bing.com/th/id/OIP.I0RDoInlmsTclEdtmFAaigHaHa?pid=ImgDet&rs=1",
                "My love",
                current,
                "6:30"
            )
        )
        list.add(
            HomeModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My love",
                current,
                "6:30"
            )
        )
        list.add(
            HomeModel(
                "https://th.bing.com/th/id/OIP.I0RDoInlmsTclEdtmFAaigHaHa?pid=ImgDet&rs=1",
                "My love",
                current,
                "6:30"
            )
        )
        list.add(
            HomeModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My love",
                current,
                "6:30"
            )
        )
        list.add(
            HomeModel(
                "https://th.bing.com/th/id/OIP.I0RDoInlmsTclEdtmFAaigHaHa?pid=ImgDet&rs=1",
                "My love",
                current,
                "6:30"
            )
        )
        list.add(
            HomeModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My love",
                current,
                "6:30"
            )
        )
        list.add(
            HomeModel(
                "https://th.bing.com/th/id/OIP.I0RDoInlmsTclEdtmFAaigHaHa?pid=ImgDet&rs=1",
                "My love",
                current,
                "6:30"
            )
        )
        list.add(
            HomeModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My love",
                current,
                "6:30"
            )
        )
        list.add(
            HomeModel(
                "https://th.bing.com/th/id/OIP.I0RDoInlmsTclEdtmFAaigHaHa?pid=ImgDet&rs=1",
                "My love",
                current,
                "6:30"
            )
        )
        list.add(
            HomeModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My love",
                current,
                "6:30"
            )
        )
        list.add(
            HomeModel(
                "https://th.bing.com/th/id/OIP.I0RDoInlmsTclEdtmFAaigHaHa?pid=ImgDet&rs=1",
                "My love",
                current,
                "6:30"
            )
        )
        _listLiveRL.postValue(list)
    }

    private val _listLiveTA = MutableLiveData<List<HomeModel>>().apply { }
    val _listDataTA: LiveData<List<HomeModel>> = _listLiveTA
    fun fetchDataAlbumTA() {
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
        _listLiveTA.postValue(list)
    }

    fun loadTopAlbumAsset() {
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
                year = "2023"
                mListDataAsset.add(HomeModel(img, name, year))
            }
            _listLiveTA.postValue(mListDataAsset)

        } catch (ioe: IOException) {
            Log.e("ppp", "Could not list assets", ioe)
            return
        }
    }
}