package com.uits.musicplayer.ui.search


import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uits.musicplayer.model.SearchModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class SearchViewModel : ViewModel() {



    private val _listDataAlbum = MutableLiveData<List<SearchModel>>().apply {

    }
    val listDataAlbum: LiveData<List<SearchModel>> = _listDataAlbum

    fun fetchDataAlbum() {
        var current = ""
        current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm")
            LocalDateTime.now().format(formatter)
        } else {
            val time = Calendar.getInstance().time
            var formatter = SimpleDateFormat("dd-MMMM-yyyy HH:mm")
            formatter.format(time)
        }
        var listData: MutableList<SearchModel> = mutableListOf()
        listData.add(
            SearchModel(
                "Rap",
                System.currentTimeMillis().toString(),
                "https://s3.amazonaws.com/thumbnails.venngage.com/template/ef47dc15-b2f6-4e7b-99b3-fa02d910c7dc.png"

            )
        )
        listData.add(
            SearchModel(
                "Rock",
                current,
                "https://th.bing.com/th/id/R.d7cb8cfcb49781c5cbdc7d9500ddf8b0?rik=7JWIpP0EGSK5ng&riu=http%3a%2f%2fwww.foidesigns.com%2fwp-content%2fuploads%2f2012%2f07%2fVolume-Stat-Music-atlanta-company-atlanta-designer-branding-christian-logos-Church-city-custom-business-cards-custom-flyer-custom-graphic-designer-custom-graphic-logo-custom-logo-design-faith-design-foi-des.jpg&ehk=MAi1TI8YE126uyaZ%2bKXVHerlroVO%2fKhfp3A6jfBlbOc%3d&risl=&pid=ImgRaw&r=0"
            )

        )
        listData.add(
            SearchModel(
                "Dance",
                System.currentTimeMillis().toString(),
                "https://s3.amazonaws.com/thumbnails.venngage.com/template/ef47dc15-b2f6-4e7b-99b3-fa02d910c7dc.png"

            )
        )
        listData.add(
            SearchModel(
                "Blues",
                current,
                "https://th.bing.com/th/id/R.d7cb8cfcb49781c5cbdc7d9500ddf8b0?rik=7JWIpP0EGSK5ng&riu=http%3a%2f%2fwww.foidesigns.com%2fwp-content%2fuploads%2f2012%2f07%2fVolume-Stat-Music-atlanta-company-atlanta-designer-branding-christian-logos-Church-city-custom-business-cards-custom-flyer-custom-graphic-designer-custom-graphic-logo-custom-logo-design-faith-design-foi-des.jpg&ehk=MAi1TI8YE126uyaZ%2bKXVHerlroVO%2fKhfp3A6jfBlbOc%3d&risl=&pid=ImgRaw&r=0"
            )
        )
        listData.add(
            SearchModel(
                "Jazz",
                current,
                "https://s3.amazonaws.com/thumbnails.venngage.com/template/ef47dc15-b2f6-4e7b-99b3-fa02d910c7dc.png"
            )
        )
        _listDataAlbum.postValue(
            listData
        )
    }

}