package com.example.movie_android.data.remote.service

import com.example.movie_android.data.remote.model.MovieResponse
import com.example.movie_android.util.Contansts.Companion.API_KEY
import com.example.movie_android.util.Contansts.Companion.LANG_PTBR
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IMovieApiService {

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") lang: String = LANG_PTBR,
        @Query("page") page: Int
    ): Response<MovieResponse>
}