package com.uits.musicplayer.ui.home

import android.annotation.SuppressLint
import android.app.Application
import android.content.res.AssetManager
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.uits.musicplayer.database.entities.Favorite
import com.uits.musicplayer.database.entities.RecentHistory
import com.uits.musicplayer.database.entities.RecentListenings
import com.uits.musicplayer.database.repository.FavoriteRepository
import com.uits.musicplayer.database.repository.RecentHistoryRepository
import com.uits.musicplayer.database.repository.RecentListeningRepository
import com.uits.musicplayer.databinding.FragmentHomeBinding
import com.uits.musicplayer.model.AlbumModel
import com.uits.musicplayer.model.HomeModel
import com.uits.musicplayer.model.MusicModel
import com.uits.musicplayer.service.APIClient
import com.uits.musicplayer.service.response.MusicResponse
import com.uits.musicplayer.service.response.WeatherResponse
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
import java.util.Locale
import kotlin.math.log

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _list = MutableLiveData<List<HomeModel>>().apply {

    }
    var mListDataAsset: MutableList<HomeModel> = mutableListOf()
    val context = getApplication<Application>().applicationContext
    var mAssets: AssetManager = context.assets
    val _liveData: LiveData<List<HomeModel>> = _list
    private val _listLiveRL = MutableLiveData<List<HomeModel>>().apply { }
    val _listDataRL: LiveData<List<HomeModel>> = _listLiveRL
    private val _listLiveTA = MutableLiveData<List<HomeModel>>().apply { }
    val _listDataTA: LiveData<List<HomeModel>> = _listLiveTA


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
            if (i == 0 || mListDataAsset[i].nameAlbum != mListDataAsset[i - 1].nameAlbum)
                list.add(mListDataAsset[i])
        }
        mListDataAsset.clear()
        mListDataAsset.addAll(list)
        _listLiveTA.postValue(mListDataAsset)
    }

    private fun onFail(t: Throwable) {
        print(t.message)
    }

    fun fetchDataAlbum(listId:MutableList<String>) {
        val database = Firebase.database
        val myRef = database.getReference("music")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val list: MutableList<HomeModel> = mutableListOf()
                val list2: MutableList<HomeModel> = mutableListOf()

                dataSnapshot.children.forEach {
                    val id=it.child("id").value.toString()
                  //  val liveData=getAlbumR(id)
                    for (i in listId){
                        if (i==id){
                            val title = it.child("album").value.toString()
                            val img = it.child("image").value.toString()
                            val year = "2023"
                            list.add(HomeModel(img, title, year))
                        }
                    }
                }
                Log.d("ppp",list.size.toString())
                list.sortBy { it.nameAlbum }
                for (i in list.indices) {
                    if (i == 0 || list[i].nameAlbum != list[i - 1].nameAlbum)
                        list2.add(list[i])
                }
                list.clear()
                list.addAll(list2)
                _list.postValue(list)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }


    private val recentHistoryRespository: RecentListeningRepository = RecentListeningRepository(
        application
    )

    //
    fun insert(recentListenings: RecentListenings) =
        MainScope().launch(Dispatchers.IO) {
            recentHistoryRespository.insert(recentListenings)
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
            LiveData<List<RecentListenings>> = recentHistoryRespository.get()


    val listId= mutableListOf<String>()
    fun getAlbumR(id:String): LiveData<String> {
        val resultLiveData = MutableLiveData<String>()
        val list= mutableListOf<RecentListenings>()
        recentHistoryRespository.get().observeForever {
            list.addAll(it)
            for (i in list){
                listId.add(i.id)
            }
            val result=checkId(id)
            resultLiveData.postValue(result)

        }
        return resultLiveData
    }
    fun checkId(id:String): String {
        for (i in listId){
            if (i==id)
                return i
        }
        return ""
    }
    fun duration(link:String): String {
        val mediaPlayer=MediaPlayer()
        try {
            mediaPlayer.setDataSource(link)
            mediaPlayer.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val durationInMillis = mediaPlayer.duration ?: 0
        val minutes = (durationInMillis / 1000) / 60
        val seconds = (durationInMillis / 1000) % 60
        val m_s =  String.format("%02d:%02d", minutes, seconds)
        return m_s
    }



    fun loadTopAlbumAsset() {
        mListDataAsset.clear()
        val soundName: Array<String>?
        var name: String
        var img: String
        var year: String

        try {
            soundName = mAssets.list("album")

            // mListDataAsset.add(soundName)
            soundName?.forEach {
                name = it
                img =
                    "https://th.bing.com/th/id/R.6890c58344eb146bc1ec0d40b27e356f?rik=wQULtPjtBD6PiA&pid=ImgRaw&r=0"
                year = "2023"
                mListDataAsset.add(HomeModel(img, name, year))
            }
            _listLiveTA.postValue(mListDataAsset)

        } catch (ioe: IOException) {
            Log.e("ppp", "Could not list assets", ioe)
            return
        }
    }
    val favoriteRepository = FavoriteRepository(
        context
    )
    fun insertF(favorite: Favorite) =
        MainScope().launch(Dispatchers.IO) {
            favoriteRepository.insert(favorite)
        }
    fun deleteIdF(id:String){
        MainScope().launch(Dispatchers.IO){
            favoriteRepository.deleteId(id)
        }
    }
    fun getFavorit():
        LiveData<List<Favorite>> =favoriteRepository.get()

}


