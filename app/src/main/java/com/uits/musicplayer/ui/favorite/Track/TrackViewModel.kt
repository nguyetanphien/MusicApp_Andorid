package com.uits.musicplayer.ui.favorite.Track

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uits.musicplayer.model.ArtistModel
import com.uits.musicplayer.model.HomeModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class TrackViewModel:ViewModel() {
    val _mListDataLive=MutableLiveData<List<ArtistModel>>().apply {  }
    val _listLive: LiveData<List<ArtistModel>> =_mListDataLive
    fun fetchTrackList() {
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
                "ST",
                current,
                "3.14"
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                "ST",
                current,
                "3.14"
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                "ST",
                current,
                "3.14"
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                "ST",
                current,
                "3.14"
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                "ST",
                current,
                "3.14"
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                "ST",
                current,
                "3.14"
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                "ST",
                current,
                "3.14"
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                "ST",
                current,
                "3.14"
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                "ST",
                current,
                "3.14"
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                "ST",
                current,
                "3.14"
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                "ST",
                current,
                "3.14"
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                "ST",
                current,
                "3.14"
            )
        )
        list.add(
            ArtistModel(
                "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0",
                "My song",
                "ST",
                current,
                "3.14"
            )
        )

        _mListDataLive.postValue(list)
    }
}