package com.uits.musicplayer.service


import com.uits.musicplayer.model.MusicModel
import com.uits.musicplayer.service.response.MusicResponse
import com.uits.musicplayer.service.response.WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {
    @GET("weather")
    fun getWeather() : Observable<WeatherResponse>
    @GET("/uamp/catalog.json")
    fun getMusic() : Observable<MusicResponse>
}