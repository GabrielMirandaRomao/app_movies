package com.example.movie_android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movie_android.data.remote.model.Movie
import com.example.movie_android.databinding.ItemMovieBinding
import com.example.movie_android.ui.HomeFragmentDirections
import com.example.movie_android.util.Contansts.Companion.POSTER_BASE_URL
import com.example.movie_android.util.Utils.Companion.toGenre

class MovieAdapter() : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movieData = emptyList<Movie>()

    class MovieViewHolder(val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val item = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(item)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.binding.apply {
            if (movieData.isNotEmpty()) {
                tvTitle.text = movieData[position].title
            }

            if (movieData[position].genre_ids?.isEmpty() == true){
                tvGenre.text = "No Gender"
            }else{
                tvGenre.text = toGenre(movieData[position].genre_ids?.get(0))
            }

            tvRelease.text = movieData[position].release_date

            Glide.with(ivBanner)
                .load(POSTER_BASE_URL + movieData[position].poster_path)
                .into(ivBanner)

        }

        holder.itemView.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeMoviesToMovieDetail(movieData[position])
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount() = movieData.size

    fun updateMovie(movies: List<Movie>) {
        this.movieData = movies
        notifyDataSetChanged()
    }
}