package com.uits.musicplayer.ui.favorite

import android.annotation.SuppressLint
import android.app.Application
import android.content.res.AssetManager
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.uits.musicplayer.model.AlbumModel
import com.uits.musicplayer.model.HomeModel
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

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val mListLiveData = MutableLiveData<List<HomeModel>>().apply { }
    val listLive: LiveData<List<HomeModel>> = mListLiveData
    var mListDataAsset: MutableList<HomeModel> = mutableListOf()
    @SuppressLint("StaticFieldLeak")
    val context = getApplication<Application>().applicationContext
    var mAssets: AssetManager = context.assets
    private val _listLiveRL = MutableLiveData<List<HomeModel>>().apply { }
    val _listDataRL: LiveData<List<HomeModel>> = _listLiveRL
    fun fetchDataplayList() {


        val database = Firebase.database
        val myRef = database.getReference("music")
        // Read from the database

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val list: MutableList<HomeModel> = mutableListOf()
                val list2: MutableList<HomeModel> = mutableListOf()
                dataSnapshot.children.forEach {
                    if (it.child("playlist").value.toString() != "") {
                        val title = it.child("playlist").value.toString()
                        //  Log.d("ppp",title)
                        val img = it.child("image").value.toString()
                        val year = "2023"
                        list.add(HomeModel(img, title, year))
                    }
                }
                list.sortBy { it.nameAlbum }
                for (i in list.indices) {
                    if (i == 0 || list[i].nameAlbum != list[i - 1].nameAlbum) {
                        list2.add(list[i])
                    }
                }
                list.clear()
                list.addAll(list2)
                mListLiveData.postValue(list)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("qqq", "Failed to read value.", error.toException())
            }
        })
    }

    private val mListLiveDataAl = MutableLiveData<List<HomeModel>>().apply { }
    val listLiveAl: LiveData<List<HomeModel>> = mListLiveDataAl

    @SuppressLint("CheckResult")
    fun fetchDataAlbum() {
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
        mListDataAsset.clear()
        var title: String
        var img: String
        var year: String
        val list = mutableListOf<HomeModel>()
        response.music.forEach {
            title = it.album
            img = it.image
            year = "2023"
            mListDataAsset.add(HomeModel(img, title, year))
        }
        mListDataAsset.sortBy { it.nameAlbum }


        for (i in mListDataAsset.indices) {
            if (i == 0 || mListDataAsset[i].nameAlbum != mListDataAsset[i - 1].nameAlbum) list.add(
                mListDataAsset[i]
            )

        }
        mListDataAsset.clear()
        mListDataAsset.addAll(list)
        mListLiveDataAl.postValue(mListDataAsset)
    }

    private fun onFail(t: Throwable) {
        print(t.message)
    }


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