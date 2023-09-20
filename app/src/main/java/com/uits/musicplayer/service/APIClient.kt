package com.uits.musicplayer.service

import android.util.Log
import com.intuit.sdp.BuildConfig
import com.uits.musicplayer.service.APIClient.APIClient.mApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale
import java.util.concurrent.TimeUnit

class APIClient {
    private val BASE_URL = "https://storage.googleapis.com/"
    private val TAG: String = APIClient::class.java.getSimpleName()
    private val TIME_OUT: Long = 300000
    private val AUTHORIZATION = "x-access-token"
    private val AUTHORIZATION_TYPE = "Bearer "


    object APIClient{
        lateinit var mApiService : ApiService
    }

    fun init() {

        // init OkHttpClient
        // init OkHttpClient
        val okHttpBuilder = OkHttpClient().newBuilder()
        okHttpBuilder.connectTimeout(
            TIME_OUT,
            TimeUnit.MILLISECONDS
        )
        okHttpBuilder.retryOnConnectionFailure(true)
        okHttpBuilder.interceptors().add(ForbiddenInterceptor())

        // Log
        if (BuildConfig.DEBUG) {
            val logInterceptor = HttpLoggingInterceptor()
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpBuilder.interceptors().add(logInterceptor)
        }
        // AUTHORIZATION
        Log.d(
            "TAG",
            "intercept: " + Locale.getDefault().isO3Language.substring(0, 2)
        )
        okHttpBuilder.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header(AUTHORIZATION, "token")
                .addHeader("Accept-Language", Locale.getDefault().language)
                .header("Connection", "close")
                .header("x-rapidapi-key", "a6725ef7bemshf6e9dba94878d91p19c996jsnd3f701cabe36")
                .method(original.method(), original.body())
            val request = requestBuilder.build()
            chain.proceed(request)
        }

        var retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpBuilder.build()).build()

        mApiService = retrofit.create(ApiService::class.java)
    }
}