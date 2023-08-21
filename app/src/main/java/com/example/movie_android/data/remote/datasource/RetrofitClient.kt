package com.example.movie_android.data.remote.datasource

import com.example.movie_android.data.remote.service.IMovieApiService
import com.example.movie_android.util.Contansts.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val retrofit by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client  )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val createService: IMovieApiService by lazy {
        retrofit.create(IMovieApiService::class.java)
    }

}