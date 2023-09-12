package com.uits.musicplayer.ui.album

import android.app.Application
import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.AudioManager
import android.media.MediaMetadataRetriever
import android.media.SoundPool
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.uits.musicplayer.model.AlbumModel
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar


class AlbumViewModel(application: Application) : AndroidViewModel(application) {

    private val mLiveData = MutableLiveData<List<AlbumModel>>().apply { }
    val liveData: LiveData<List<AlbumModel>> = mLiveData
    var mListDataAsset: MutableList<AlbumModel> = mutableListOf()
    val context = getApplication<Application>().applicationContext
    var mAssets: AssetManager = context.assets

    init {
        //   loadSounds()
    }

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
                mListDataAsset.add(AlbumModel(name, singer, link, time))
            }
            mLiveData.postValue(mListDataAsset)

        } catch (ioe: IOException) {
            Log.e("ppp", "Could not list assets", ioe)
            return
        }
    }

    fun getMp3Duration(filePath: String): String {

        var metaRetriever: MediaMetadataRetriever = MediaMetadataRetriever()
        try {
            metaRetriever.setDataSource(filePath)
        } catch (a: Exception) {
            Log.e("ppp", "a.toString()")
        }


        var out: String = ""
        var txtTime: String = ""

        var duration: String? =
            metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)

        if (duration != null) {
            Log.d("DURATION VALUE", duration)
        }

        var dur: Long = duration?.toLong() ?: 3
        var seconds: String = ((dur % 60000) / 1000).toString()

        Log.d("SECONDS VALUE", seconds)

        var minutes: String = (dur / 60000).toString()
        out = "$minutes:$seconds"

        txtTime = if (seconds.length == 1) {
            "0$minutes:0$seconds"
        } else {
            "0$minutes:$seconds"
        }
        return txtTime
    }
}
