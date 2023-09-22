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
import com.uits.musicplayer.model.HomeModel
import com.uits.musicplayer.model.SearchModel
import com.uits.musicplayer.service.APIClient
import com.uits.musicplayer.service.response.MusicResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class SearchViewModel(application: Application) : AndroidViewModel(application) {


    private val _listDataAlbum = MutableLiveData<List<SearchModel>>().apply {
    }
    private val mLiveData = MutableLiveData<List<AlbumModel>>().apply { }

    val liveData: LiveData<List<AlbumModel>> = mLiveData
    var mListDataApi: MutableList<SearchModel> = mutableListOf()
    val context = getApplication<Application>().applicationContext
    val listDataAlbum: LiveData<List<SearchModel>> = _listDataAlbum
    var mAssets: AssetManager = context.assets
    @SuppressLint("CheckResult")
    fun featchData() {
        val response: Observable<MusicResponse> = APIClient.APIClient.mApiService.getMusic()
        response.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                run {
                    onSuccess(response)
                }
            }, { t ->
                run {
                    onFail(t)
                }
            })
    }
    private fun onSuccess(response: MusicResponse) {
        mListDataApi.clear()
        var title: String
        var img: String
        val list= mutableListOf<SearchModel>()
        response.music.forEach {
            title=it.genre
            img =it.image

            mListDataApi.add(SearchModel(title,img))
        }
        mListDataApi.sortBy { it.title }

        for (i in mListDataApi.indices){
            if (i==0||mListDataApi[i].title != mListDataApi[i-1].title)
                list.add(mListDataApi[i])
        }
        mListDataApi.clear()
        mListDataApi.addAll(list)
        _listDataAlbum.postValue(mListDataApi)
    }

    private fun onFail(t: Throwable) {
        print(t.message)
    }

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