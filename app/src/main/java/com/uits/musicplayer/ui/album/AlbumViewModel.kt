package com.uits.musicplayer.ui.album

import android.annotation.SuppressLint
import android.app.Application
import android.content.res.AssetManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.uits.musicplayer.model.AlbumModel
import com.uits.musicplayer.service.APIClient
import com.uits.musicplayer.service.response.MusicResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException


class AlbumViewModel(application: Application) : AndroidViewModel(application) {

    private val mLiveData = MutableLiveData<List<AlbumModel>>().apply { }
    val liveData: LiveData<List<AlbumModel>> = mLiveData
    var mListDataAsset: MutableList<AlbumModel> = mutableListOf()
    val context = getApplication<Application>().applicationContext
    var mAssets: AssetManager = context.assets


    @SuppressLint("CheckResult")
    fun featchData(nameAlbum: String) {
        val response: Observable<MusicResponse> = APIClient.APIClient.mApiService.getMusic()
        response.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                run {
                    onSuccess(response,nameAlbum)
                }
            }, { t ->
                run {
                    onFail(t)
                }
            })
    }
    private fun onSuccess(response: MusicResponse, nameAlbum: String) {
        mListDataAsset.clear()
        var title: String
        var nameSinger: String
        var link: String
        var time:String
        var images: String
        response.music.forEach {
            if(it.album==nameAlbum){
                title=it.title
                nameSinger =it.artist
                link=it.source
                time="3.20"
                images=it.image
                mListDataAsset.add(AlbumModel(title,nameSinger,link, time,images))
            }
        }

        mLiveData.postValue(mListDataAsset)
    }

    private fun onFail(t: Throwable) {
        print(t.message)
    }


    fun loadSounds(nameAlbum: String) {
        mListDataAsset.clear()
        val soundName: Array<String>?

        var edm: Array<String>? = null
        var name: String
        var singer: String
        var link: String
        var time: String
        var input: Array<String>
        try {
            soundName = mAssets.list("album")
            Log.i("ppp", "Found" + soundName!!.size + " sounds")
            // mListDataAsset.add(soundName)
            soundName.forEach { _ ->
                edm = mAssets.list("album/$nameAlbum")

            }
            Log.e("ppp", edm?.size.toString())
            edm?.forEach {
                input = it.split("-").toTypedArray()
                name = (input[0])
                singer = (input[1])
                // Log.e("ppp", getMp3Duration("assets/album/edm/$it"))
                link = ("album/$nameAlbum/$it")
                time = ("3:12")
                mListDataAsset.add(AlbumModel(name, singer, link, time,""))
            }
            mLiveData.postValue(mListDataAsset)

        } catch (ioe: IOException) {
            Log.e("ppp", "Could not list assets", ioe)
            return
        }
    }


}
