package com.uits.musicplayer.ui.favorite.Track

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uits.musicplayer.database.entities.Favorite
import com.uits.musicplayer.database.entities.RecentListenings
import com.uits.musicplayer.database.repository.FavoriteRepository
import com.uits.musicplayer.database.repository.RecentListeningRepository
import com.uits.musicplayer.model.AlbumModel
import com.uits.musicplayer.model.ArtistModel
import com.uits.musicplayer.model.HomeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope

import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar


class TrackViewModel(application: Application) : AndroidViewModel(application) {
    val _mListDataLive = MutableLiveData<List<AlbumModel>>().apply { }
    val context = getApplication<Application>().applicationContext
    val _listLive: LiveData<List<AlbumModel>> = _mListDataLive

    private val favoriteRepository: FavoriteRepository = FavoriteRepository(
        context
    )

    fun insert(favorite: Favorite) =
        MainScope().launch(Dispatchers.IO) {
            favoriteRepository.insert(favorite)
        }

    fun deleteid(id: String) =
        MainScope().launch(Dispatchers.IO) {
            favoriteRepository.deleteId(id)
        }

    fun delete() =
        MainScope().launch(Dispatchers.IO) {
            favoriteRepository.delete()
        }

    fun getDAta():
            LiveData<List<Favorite>> = favoriteRepository.get()
    var listAlbum:MutableList<AlbumModel>  = mutableListOf()
    var list = mutableListOf<Favorite>()
    fun loadData() {

        favoriteRepository.get().observeForever { data ->
            list.clear()
            list.addAll(data)

        }




    Log.d("ppp",listAlbum.size.toString())
    _mListDataLive.postValue(listAlbum)

}

}