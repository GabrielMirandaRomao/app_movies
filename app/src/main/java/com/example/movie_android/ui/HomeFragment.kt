package com.example.movie_android.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie_android.data.local.database.MovieDatabase
import com.example.movie_android.data.repository.MovieRepository
import com.example.movie_android.databinding.FragmentHomeBinding
import com.example.movie_android.ui.adapter.MovieAdapter
import com.example.movie_android.util.Resource

class HomeFragment() : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val adapter: MovieAdapter by lazy { MovieAdapter() }
    private lateinit var homeViewModel: HomeViewModel

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val movieRepository = MovieRepository(MovieDatabase(requireContext()))
        val viewModelProviderFactory = MovieViewModelProvideFactory(movieRepository)
        homeViewModel = ViewModelProvider(
            requireActivity(),
            viewModelProviderFactory
        )[HomeViewModel::class.java]

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        setListener()
        observe()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvMovies.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMovies.adapter = adapter
    }

    private fun observe() {
        homeViewModel.nowPlaying.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { nowPlaying ->
                        adapter.updateMovie(nowPlaying.results.toList())
                        val totalPages = nowPlaying.totalPages / 2
                        isLastPage = homeViewModel.currentPage == totalPages
                    }
                }

                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.d("homeViewModel", message)
                    }
                }

                is Resource.Loading -> {
//                    showProgressBar()
                }
            }
        }
    }

    private fun setListener() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null){
                    searchThroughDatabase(query)
                }

                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null){
                    searchThroughDatabase(query)
                }
                return true
            }

        })
    }

    private fun searchThroughDatabase(query: String) {
        val searchQuery = "%$query%"
        homeViewModel.searchDatabase(searchQuery).observe(this) { list ->
            list?.let {
                adapter.updateMovie(it)
            }
        }
    }

}