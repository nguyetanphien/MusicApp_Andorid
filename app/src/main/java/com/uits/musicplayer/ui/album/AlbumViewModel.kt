package com.uits.musicplayer.ui.album

import android.content.ContentValues.TAG
import android.content.Context
import android.content.res.AssetManager
import android.nfc.Tag
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uits.musicplayer.model.AlbumModel
import java.io.IOException

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class AlbumViewModel():ViewModel() {
   // val context:Context

    private val mLiveData=MutableLiveData<List<AlbumModel>>().apply {  }
    val liveData:LiveData<List<AlbumModel>> = mLiveData
    fun fetchAlbumList() {
        var current = ""
        current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var formatter = DateTimeFormatter.ofPattern("yyyy")
            LocalDateTime.now().format(formatter)
        } else {
            val time = Calendar.getInstance().time
            var formatter = SimpleDateFormat("yyyy")
            formatter.format(time)
        }
        val list: MutableList<AlbumModel> = mutableListOf()
        list.add(
            AlbumModel(

                "My song",
                "ST",
                current,
                "3.14"
            )
        )

        list.add(
            AlbumModel(

                "My song",
                "ST",
                current,
                "3.14"
            )
        )
        list.add(
            AlbumModel(

                "My song",
                "ST",
                current,
                "3.14"
            )
        )
        list.add(
            AlbumModel(

                "My song",
                "ST",
                current,
                "3.14"
            )
        )
        list.add(
            AlbumModel(

                "My song",
                "ST",
                current,
                "3.14"
            )
        )
        list.add(
            AlbumModel(

                "My song",
                "ST",
                current,
                "3.14"
            )
        )
        list.add(
            AlbumModel(

                "My song",
                "ST",
                current,
                "3.14"
            )
        )
        list.add(
            AlbumModel(

                "My song",
                "ST",
                current,
                "3.14"
            )
        )
        list.add(
            AlbumModel(

                "My song",
                "ST",
                current,
                "3.14"
            )
        )
        list.add(
            AlbumModel(

                "My song",
                "ST",
                current,
                "3.14"
            )
        )
        list.add(
            AlbumModel(

                "My song",
                "ST",
                current,
                "3.14"
            )
        )



        mLiveData.postValue(list)
    }
  //  var mListDataAsset = mutableListOf<AlbumModel>()
  //  var mAssets: AssetManager =context.assets
//     fun loadSounds(){
//        val soundName: Array<String>?
//        try {
//            soundName= mAssets.list("edm")
//            Log.i("ppp","Found"+soundName!!.size+" sounds")
//           // mListDataAsset.add(soundName)
//
//        }catch (ioe:IOException){
//            Log.e("ppp","Could not list assets",ioe)
//            return
//        }
//    }

}




