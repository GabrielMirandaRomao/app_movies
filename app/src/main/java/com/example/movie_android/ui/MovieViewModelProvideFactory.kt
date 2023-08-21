package com.example.movie_android.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movie_android.data.repository.MovieRepository

class MovieViewModelProvideFactory(
    private val movieRepository: MovieRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(movieRepository) as T
    }
}