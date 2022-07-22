package com.podium.technicalchallenge.ui.discover

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.podium.technicalchallenge.DemoViewModel
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.databinding.FragmentDiscoverBinding
import com.podium.technicalchallenge.entity.MovieEntity
import com.podium.technicalchallenge.ui.dashboard.MoviesAdapter
import androidx.recyclerview.widget.GridLayoutManager

class DiscoverFragment: Fragment() {

    private val viewModel: DemoViewModel by activityViewModels()
    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!

    var _filteredAdapter: MoviesAdapter? = null
    private val adapter get() = _filteredAdapter!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDiscoverBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        _filteredAdapter = MoviesAdapter(this)
        binding.filteredMoviesList.adapter = _filteredAdapter
        val layoutManager = GridLayoutManager(requireContext(), 3)
        binding.filteredMoviesList.layoutManager = layoutManager

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.liveGenres.observe(viewLifecycleOwner,  {
            binding.genreButtonsList.removeAllViews()
            it?.forEach {
                val chip = layoutInflater.inflate(R.layout.standalone_chip, binding.genreButtonsList, false) as Chip
                chip.text = it
                viewModel.liveGenresSelected.value?.let {
                }
                binding.genreButtonsList.addView(chip as View)
            }

            // Set the previous selected genres when coming back to the page or from the movie info
            // page
            binding.genreButtonsList.forEach {btn ->
                when (btn) {
                    is Chip -> {
                        viewModel.liveGenresSelected.value?.let {genres ->
                            if (genres.contains(btn.text)) btn.isChecked = true

                        }
                    }
                }
            }


        })

        binding.genreButtonsList.setOnCheckedStateChangeListener { group, checkedIds ->
            var selectedGenres = mutableListOf<String>()
            // use the genre and the id in a hashmap
            checkedIds.forEach {id ->
                var genre = binding.genreButtonsList.findViewById<Chip>(id)
                selectedGenres.add(genre.text.toString())
            }
            viewModel.liveGenresSelected.value = selectedGenres
            val res = searchByGenres(selectedGenres)

            res?.let { _filteredAdapter?.updateData(it) }
        }

        // Get all the movies
        viewModel.getMovies()
    }

    fun displayMovieInfo(movie: MovieEntity) {
        viewModel.liveSelectedMovie.value = movie
        findNavController().navigate(R.id.movieInfoFragment)
    }

    // Selected Genres
    fun searchByGenres(genres: List<String>): List<MovieEntity>? {
        // Cases
        // Soul is Animated and Comedy   genres selected are [comedy] = soul is filtered
        // Soul is Animated and Comedy   genres selected are [animated, comedy] = soul is filtered
        // Soul is Animated and Comedy   genres selected are [animated, comedy, horror] = soul is NOT filtered
        val filteredByGenre = viewModel.liveAllMovies.value?.filter {
            var containsAllGenres = true
            genres.forEach {genre ->
                if (!it.genres.contains(genre)) {
                    containsAllGenres = false
                }
            }
            it.genres.intersect(genres.toSet()).isNotEmpty() && containsAllGenres
        }
        return filteredByGenre
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = DiscoverFragment()
    }
}