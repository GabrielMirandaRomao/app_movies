package com.example.movie_android.data.repository

import androidx.lifecycle.LiveData
import com.example.movie_android.data.local.database.MovieDatabase
import com.example.movie_android.data.remote.model.Movie
import com.example.movie_android.data.remote.datasource.RetrofitClient

class MovieRepository(
    private val movieDatabase: MovieDatabase
) {
    suspend fun getNowPlaying(pageNumber: Int) =
        RetrofitClient.createService.getNowPlaying(page = pageNumber)

    fun searchDatabase(searchQuery: String): LiveData<List<Movie>> {
        return movieDatabase.getMovieDao().searchDatabase(searchQuery)
    }
}