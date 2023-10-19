package com.uits.musicplayer.ui.album

import android.annotation.SuppressLint
import android.app.Application
import android.content.res.AssetManager
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.uits.musicplayer.database.entities.Favorite
import com.uits.musicplayer.database.repository.FavoriteRepository
import com.uits.musicplayer.model.AlbumModel
import com.uits.musicplayer.service.APIClient
import com.uits.musicplayer.service.response.MusicResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.IOException


@SuppressLint("StaticFieldLeak")
class AlbumViewModel(application: Application) : AndroidViewModel(application) {

    private val mLiveData = MutableLiveData<List<AlbumModel>>().apply { }

    val liveData: LiveData<List<AlbumModel>> = mLiveData

    var mListDataAsset: MutableList<AlbumModel> = mutableListOf()
    val context = getApplication<Application>().applicationContext
    var mAssets: AssetManager = context.assets
    var m_s = ""
    val favoriteRepository = FavoriteRepository(
        context
    )

    @SuppressLint("CheckResult")
    fun featchData(nameAlbum: String) {
        val response: Observable<MusicResponse> = APIClient.APIClient.mApiService.getMusic()
        response.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                run {
                    onSuccess(response, nameAlbum)
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
        var id: String
        var nameSinger: String
        var link: String
        var time: String
        var images: String
        response.music.forEach {
            if (it.album == nameAlbum) {
                id = it.id
                title = it.title
                nameSinger = it.artist
                link = it.source
                time = "0"
                images = it.image
                mListDataAsset.add(AlbumModel(id, title, nameSinger, link, time, images))
            }
        }
        mLiveData.postValue(mListDataAsset)
    }

    private fun onFail(t: Throwable) {
        print(t.message)
    }

    fun loadDataFireBase(nameAlbum: String) {
        val database = Firebase.database
        val myRef = database.getReference("music")
        // Read from the database


        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var list: MutableList<AlbumModel> = mutableListOf()
                var title: String
                var id: String
                var nameSinger: String
                var link: String
                var time: String
                var images: String
                dataSnapshot.children.forEach {
                    if (it.child("album").value.toString() == nameAlbum || it.child("playlist").value.toString() == nameAlbum) {
                        id = it.child("id").value.toString()
                        title = it.child("title").value.toString()
                        nameSinger = it.child("artist").value.toString()
                        link = it.child("source").value.toString()
                        time = startTrackingTime(it.child("duration").value.toString())
                        images = it.child("image").value.toString()
                        list.add(AlbumModel(id, title, nameSinger, link, time, images))
                    }
                }
                mLiveData.postValue(list)

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("qqq", "Failed to read value.", error.toException())
            }
        })
    }

    private fun startTrackingTime(durationInMillis: String): String {
        val duration = durationInMillis.toInt()
        val minutes = duration / 60
        val seconds = duration % 60
        val s = String.format("%02d:%02d", minutes, seconds)
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

//    fun loadSounds(nameAlbum: String) {
//        mListDataAsset.clear()
//        val soundName: Array<String>?
//        var edm: Array<String>? = null
//        var name: String
//        var singer: String
//        var link: String
//        var time: String
//        var input: Array<String>
//        try {
//            soundName = mAssets.list("album")
//            Log.i("ppp", "Found" + soundName!!.size + " sounds")
//            // mListDataAsset.add(soundName)
//            soundName.forEach { _ ->
//                edm = mAssets.list("album/$nameAlbum")
//
//            }
//            Log.e("ppp", edm?.size.toString())
//            edm?.forEach {
//                input = it.split("-").toTypedArray()
//                name = (input[0])
//                singer = (input[1])
//                // Log.e("ppp", getMp3Duration("assets/album/edm/$it"))
//                link = ("album/$nameAlbum/$it")
//                time = ("3:12")
//                mListDataAsset.add(AlbumModel(name, singer, link, time, ""))
//            }
//            mLiveData.postValue(mListDataAsset)
//
//        } catch (ioe: IOException) {
//            Log.e("ppp", "Could not list assets", ioe)
//            return
//        }
//    }


}
