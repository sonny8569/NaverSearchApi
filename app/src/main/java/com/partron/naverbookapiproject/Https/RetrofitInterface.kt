package com.partron.naverbookapiproject.Https

import com.partron.naverbookapiproject.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object  RetrofitInterface {

    //요청후 30초동안 안오면 에러 처리 위함 , 그리고 Logcat 에 Log 을 보기 위해
    private val okHttpClient : OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .readTimeout(30 , TimeUnit.SECONDS)
            .writeTimeout(30 , TimeUnit.SECONDS)
            .build()
    }


    private var instance : HttpsServices ?= null

    fun requestRetrofit() : HttpsServices {
        if(instance == null) {
            instance = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_JSON)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(HttpsServices ::class.java)
        }
        return instance!!
    }

}