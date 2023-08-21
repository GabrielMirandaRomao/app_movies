package com.example.movie_android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.movie_android.databinding.FragmentMovieDetailBinding
import com.example.movie_android.util.Contansts
import com.example.movie_android.util.Utils
import com.example.movie_android.util.Utils.Companion.toGenre

class MovieDetailFragment() : Fragment() {

    private val args: MovieDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentMovieDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(layoutInflater, container, false)
        setContents()
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        setContents()
//    }

    private fun setContents() {
        binding.tvTitle.text = args.movieInfo.title
        binding.tvOverview.text = args.movieInfo.overview
        binding.tvGenre.text = toGenre(args.movieInfo.genre_ids?.get(0))
        binding.tvRelease.text = args.movieInfo.release_date

        Glide.with(binding.ivBanner)
            .load(Contansts.POSTER_BASE_URL + args.movieInfo.backdrop_path)
            .into(binding.ivBanner)

    }
}