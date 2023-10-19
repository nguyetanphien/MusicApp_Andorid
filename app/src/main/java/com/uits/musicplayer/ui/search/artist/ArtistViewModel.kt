package com.uits.musicplayer.ui.search.artist

import android.annotation.SuppressLint
import android.app.Application
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.uits.musicplayer.database.entities.Favorite
import com.uits.musicplayer.database.repository.FavoriteRepository
import com.uits.musicplayer.model.AlbumModel
import com.uits.musicplayer.model.ArtistModel
import com.uits.musicplayer.service.APIClient
import com.uits.musicplayer.service.response.MusicResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.IOException

class ArtistViewModel(application: Application) : AndroidViewModel(application) {
    private val _list = MutableLiveData<List<ArtistModel>>().apply {

    }
    val _liveData: LiveData<List<ArtistModel>> = _list
    private var mListApi: MutableList<ArtistModel> = mutableListOf()

    private val _listLivePT = MutableLiveData<List<AlbumModel>>().apply { }
    val context = getApplication<Application>().applicationContext
    val _listDataPT: LiveData<List<AlbumModel>> = _listLivePT
    private var mListApiPT: MutableList<AlbumModel> = mutableListOf()
    val favoriteRepository = FavoriteRepository(
        context
    )

    @SuppressLint("CheckResult")
    fun featchData(genre: String) {
        val response: Observable<MusicResponse> = APIClient.APIClient.mApiService.getMusic()
        response.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                run {
                    onSuccess(response, genre)
                }
            }, { t ->
                run {
                    onFail(t)
                }
            })
    }

    private fun onSuccess(response: MusicResponse, genre: String) {
        mListApi.clear()
        var title: String
        var img: String
        var year: String
        var genre2: String
        val list = mutableListOf<ArtistModel>()
        response.music.forEach {
            title = it.album
            img = it.image
            year = "2023"
            genre2 = it.genre
            mListApi.add(ArtistModel(img, title, year, genre2))
        }
        mListApi.sortBy { it.nameAlbum }
        Log.d("ppp", mListApi.toString())
        for (i in mListApi.indices) {
            if (mListApi[i].time == genre)
                list.add(mListApi[i])

        }
        val list2 = mutableListOf<ArtistModel>()
        for (i in list.indices) {
            if (i == 0 || list[i].nameAlbum != list[i - 1].nameAlbum)
                list2.add(list[i])
        }

        mListApi.clear()
        mListApi.addAll(list2)
        _list.postValue(mListApi)
    }


    @SuppressLint("CheckResult")
    fun featchDataPT(genre: String) {
        val response: Observable<MusicResponse> = APIClient.APIClient.mApiService.getMusic()
        response.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                run {
                    onSuccess2(response, genre)
                }
            }, { t ->
                run {
                    onFail(t)
                }
            })
    }

    private fun onSuccess2(response: MusicResponse, genre: String) {
        mListApiPT.clear()
        var title: String
        var id: String
        var nameSinger: String
        var link: String
        var time: String
        var images: String
        response.music.forEach {
            if (it.genre == genre) {
                id=it.id
                title = it.title
                nameSinger = it.artist
                link = it.source
                time = it.duration?.let { it1 -> startTrackingTime(it1) }.toString()
                images = it.image
                mListApiPT.add(AlbumModel(id,title, nameSinger, link, time, images))
            }
        }
        _listLivePT.postValue(mListApiPT)
    }

    private fun onFail(t: Throwable) {
        print(t.message)
    }

    private fun startTrackingTime(durationInMillis: Int): String {
        val minutes = durationInMillis / 60
        val seconds = durationInMillis % 60
        val s = String.format("%02d:%02d", minutes, seconds)
        var m_s = ""
        m_s = s
        return m_s

    }
    fun duration(link: String): String {
        val mediaPlayer = MediaPlayer()
        try {
            mediaPlayer.setDataSource(link)
            mediaPlayer.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val durationInMillis = mediaPlayer.duration ?: 0
        val minutes = (durationInMillis / 1000) / 60
        val seconds = (durationInMillis / 1000) % 60
        var m_s = String.format("%02d:%02d", minutes, seconds)
        return m_s
    }
    fun insertF(favorite: Favorite) =
        MainScope().launch(Dispatchers.IO) {
            favoriteRepository.insert(favorite)
        }

    fun deleteIdF(id: String) {
        MainScope().launch(Dispatchers.IO) {
            favoriteRepository.deleteId(id)
        }
    }

    fun getFavorit():
            LiveData<List<Favorite>> = favoriteRepository.get()


}