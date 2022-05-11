package com.nrojiani.moviesviewpager2.ui.genres

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.nrojiani.moviesviewpager2.R
import com.nrojiani.moviesviewpager2.adapters.GenreAdapter
import com.nrojiani.moviesviewpager2.data.Resource
import com.nrojiani.moviesviewpager2.data.model.Genre
import com.nrojiani.moviesviewpager2.databinding.GenresFragmentBinding
import kotlinx.coroutines.launch
import timber.log.Timber

class GenresFragment : Fragment() {

    private lateinit var binding: GenresFragmentBinding

    private val viewModel by viewModels<GenresViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.genres_fragment, container, false)

        binding.genresList.layoutManager = LinearLayoutManager(requireContext())
        binding.genresList.adapter = GenreAdapter(
            onItemClick = { genre ->
                Timber.d("$genre clicked")
                viewModel.updateCurrentGenre(genre)
                Timber.w("TODO: navigate")
                // TODO
//                findNavController().navigate(
                // TODO
//                )
            }
        )

        subscribeUi()

        return binding.root
    }

    private fun subscribeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.genres.collect { genreNamesResource ->

                    val genres: List<Genre> = when (genreNamesResource) {
                        is Resource.Success -> genreNamesResource.data
                        is Resource.Loading -> emptyList()
                        is Resource.Failure -> {
                            // TODO: Error indicator
                            emptyList()
                        }
                    }

                    (binding.genresList.adapter as GenreAdapter)
                        .submitList(genres)
                }
            }
        }
    }
}
