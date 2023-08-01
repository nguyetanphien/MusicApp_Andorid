package com.uits.musicplayer.ui.search.artist

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uits.musicplayer.model.ArtistModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class ArtistViewModel : ViewModel() {
    private val _list = MutableLiveData<List<ArtistModel>>().apply {

    }
    val _liveData: LiveData<List<ArtistModel>> = _list
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
        val list: MutableList<ArtistModel> = mutableListOf()
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                current
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                current
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                current
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                current
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                current
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                current
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                current
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                current
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                current
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                current
            )
        )
        _list.postValue(list)
    }

    private val _listLivePT = MutableLiveData<List<ArtistModel>>().apply { }
    val _listDataPT: LiveData<List<ArtistModel>> = _listLivePT
    fun fetchDataAlbumPT() {
        var current = ""
        current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            LocalDateTime.now().format(formatter)
        } else {
            val time = Calendar.getInstance().time
            var formatter = SimpleDateFormat("dd-MM-yyyy")
            formatter.format(time)
        }
        var list: MutableList<ArtistModel> = mutableListOf()
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My love",
                current,
                "6:30"
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/OIP.I0RDoInlmsTclEdtmFAaigHaHa?pid=ImgDet&rs=1",
                "My love",
                current,
                "6:30"
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My love",
                current,
                "6:30"
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/OIP.I0RDoInlmsTclEdtmFAaigHaHa?pid=ImgDet&rs=1",
                "My love",
                current,
                "6:30"
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My love",
                current,
                "6:30"
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/OIP.I0RDoInlmsTclEdtmFAaigHaHa?pid=ImgDet&rs=1",
                "My love",
                current,
                "6:30"
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My love",
                current,
                "6:30"
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/OIP.I0RDoInlmsTclEdtmFAaigHaHa?pid=ImgDet&rs=1",
                "My love",
                current,
                "6:30"
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My love",
                current,
                "6:30"
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/OIP.I0RDoInlmsTclEdtmFAaigHaHa?pid=ImgDet&rs=1",
                "My love",
                current,
                "6:30"
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My love",
                current,
                "6:30"
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/OIP.I0RDoInlmsTclEdtmFAaigHaHa?pid=ImgDet&rs=1",
                "My love",
                current,
                "6:30"
            )
        )
        _listLivePT.postValue(list)
    }

}