package com.nrojiani.moviesviewpager2.ui.movies

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nrojiani.moviesviewpager2.data.model.Movie
import timber.log.Timber

class MovieFragmentAdapter(
    viewPagerFragment: Fragment,
) : FragmentStateAdapter(viewPagerFragment) {

    private val items: MutableList<Movie> = arrayListOf()

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment {
        Timber.d("createFragment(position = $position)")
        return MovieFragment.create(items[position])
    }

    // https://proandroiddev.com/viewpager2-and-diffutil-d853cdab5f4a
    fun setItems(newItems: List<Movie>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
