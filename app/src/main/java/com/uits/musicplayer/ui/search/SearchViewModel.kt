package com.uits.musicplayer.ui.search


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
import com.uits.musicplayer.database.entities.RecentHistory
import com.uits.musicplayer.database.repository.RecentHistoryRepository
import com.uits.musicplayer.model.AlbumModel
import com.uits.musicplayer.model.HomeModel
import com.uits.musicplayer.model.SearchModel
import com.uits.musicplayer.service.APIClient
import com.uits.musicplayer.service.response.MusicResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val recentHistoryRespository: RecentHistoryRepository =
        RecentHistoryRepository(application)
    private val _listDataAlbum = MutableLiveData<List<SearchModel>>().apply {
    }
    private val _list = MutableLiveData<List<AlbumModel>>().apply {

    }
    val _liveData: LiveData<List<AlbumModel>> = _list
    var mListDataApi: MutableList<SearchModel> = mutableListOf()

    @SuppressLint("StaticFieldLeak")
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
        val list = mutableListOf<SearchModel>()
        response.music.forEach {
            title = it.genre
            img = it.image

            mListDataApi.add(SearchModel(title, img))
        }
        mListDataApi.sortBy { it.title }

        for (i in mListDataApi.indices) {
            if (i == 0 || mListDataApi[i].title != mListDataApi[i - 1].title)
                list.add(mListDataApi[i])
        }
        mListDataApi.clear()
        mListDataApi.addAll(list)
        _listDataAlbum.postValue(mListDataApi)
    }

    private fun onFail(t: Throwable) {
        print(t.message)
    }

    fun fetchDataSearch() {

        val database = Firebase.database
        val myRef = database.getReference("music")
        // Read from the database

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var list: MutableList<AlbumModel> = mutableListOf()
                //  var list2: MutableList<HomeModel> = mutableListOf()


                dataSnapshot.children.forEach {
                    val id = it.child("id").value.toString()
                    var title = it.child("title").value.toString()
                    var img = it.child("image").value.toString()
                    var time = it.child("duration").value.toString()
                    var name = it.child("artist").value.toString()
                    var link = it.child("source").value.toString()
                    list.add(AlbumModel(id, title, name, link, time, img))
                }

                _list.postValue(list)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("qqq", "Failed to read value.", error.toException())
            }
        })

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
    fun insert(recentHistory: RecentHistory) =
        MainScope().launch(Dispatchers.IO) {
            recentHistoryRespository.insert(recentHistory)
        }

    fun deleteid(id: String) =
        MainScope().launch(Dispatchers.IO) {
            recentHistoryRespository.deleteid(id)
        }

    fun delete() =
        MainScope().launch(Dispatchers.IO) {
            recentHistoryRespository.delete()
        }

    fun getDAta():
            LiveData<List<RecentHistory>> = recentHistoryRespository.get()

}