package com.nrojiani.moviesviewpager2.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.nrojiani.moviesviewpager2.R
import com.nrojiani.moviesviewpager2.data.Resource.*
import com.nrojiani.moviesviewpager2.databinding.MoviesViewPagerFragmentBinding
import kotlinx.coroutines.launch
import timber.log.Timber

class MoviesViewPagerFragment : Fragment() {

    private lateinit var binding: MoviesViewPagerFragmentBinding

    private val viewModel by viewModels<MoviesViewModel>(
        factoryProducer = {
            val args: MoviesViewPagerFragmentArgs by navArgs()
            val selectedGenre = args.genre
            MoviesViewModel.Factory(selectedGenre)
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.movies_view_pager_fragment,
            container, false
        )

        binding.movieViewPager.adapter = MovieFragmentAdapter(this)
        observeUiState()

        return binding.root
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movies.collect { moviesResource ->
                    Timber.d("moviesResource: $moviesResource")
                    val adapter = (binding.movieViewPager.adapter as MovieFragmentAdapter)
                    when (moviesResource) {
                        is Loading -> {
                            Timber.w("Loading movies")
                            adapter.setItems(emptyList())
                        }
                        is Failure -> {
                            Timber.e("Failed to fetch movies", moviesResource.exception)
                            adapter.setItems(emptyList())
                        }
                        is Success -> {
                            Timber.d("Updated movies (count = ${moviesResource.data})")
                            adapter.setItems(moviesResource.data)
                        }
                    }
                }
            }
        }
    }
}
