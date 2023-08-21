package com.example.movie_android.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_android.data.remote.model.Movie
import com.example.movie_android.data.remote.model.MovieResponse
import com.example.movie_android.data.repository.MovieRepository
import com.example.movie_android.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(
    private val repository: MovieRepository
) : ViewModel() {
    val nowPlaying: MutableLiveData<Resource<MovieResponse>> = MutableLiveData()
    var currentPage = 1
    var movieResponse: MovieResponse? = null

    init {
        getNowPlaying()
    }

    private fun getNowPlaying() = viewModelScope.launch {
        nowPlaying.postValue(Resource.Loading())
        val response = repository.getNowPlaying(currentPage)
        nowPlaying.postValue(handleNowPlayingResponse(response))
    }

    private fun  handleNowPlayingResponse(response: Response<MovieResponse>): Resource<MovieResponse>{
        currentPage++
        if (response.isSuccessful){

            response.body()?.let {resultResponse->
                if (movieResponse == null){
                    movieResponse = resultResponse
                }else{
                    val oldState = movieResponse?.results
                    val newState = resultResponse.results
                    oldState?.addAll(newState)
                }
                return Resource.Success(movieResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Movie>> {
        return repository.searchDatabase(searchQuery)
    }


}