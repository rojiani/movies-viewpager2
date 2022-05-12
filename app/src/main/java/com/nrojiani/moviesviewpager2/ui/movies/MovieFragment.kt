package com.nrojiani.moviesviewpager2.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.nrojiani.moviesviewpager2.R
import com.nrojiani.moviesviewpager2.data.model.Movie
import com.nrojiani.moviesviewpager2.databinding.MovieFragmentBinding

private const val MOVIE_ARG = "movie"

class MovieFragment : Fragment() {

    private lateinit var binding: MovieFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.movie_fragment,
            container, false
        )

        requireArguments().takeIf { bundle ->
            bundle.containsKey(MOVIE_ARG)
        }?.let { bundle ->
            val movie: Movie = bundle[MOVIE_ARG] as Movie
            bindViews(movie)
        }

        return binding.root
    }

    private fun bindViews(movie: Movie) {
        binding.movie = movie
        binding.executePendingBindings()
    }

    companion object {
        fun create(movie: Movie): MovieFragment =
            MovieFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(MOVIE_ARG, movie)
                }
            }
    }
}
