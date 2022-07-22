package com.podium.technicalchallenge.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import com.podium.technicalchallenge.DemoViewModel
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.databinding.FragmentSearchBinding
import com.podium.technicalchallenge.entity.MovieEntity
import com.podium.technicalchallenge.network.queries.Queries
import com.podium.technicalchallenge.ui.dashboard.MoviesAdapter

class SearchFragment: Fragment() {
    private val viewModel: DemoViewModel by activityViewModels()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    var _filteredAdapter: MoviesAdapter? = null
    private val adapter get() = _filteredAdapter!!

    val liveQuery = MutableLiveData<String?>()
    val liveSort = MutableLiveData<Queries.QuerySort?>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        liveSort.value = Queries.QuerySort.Alphabetical

        binding.sortingButtonsList.setOnCheckedStateChangeListener { group, checkedIds ->

            binding.sortingButtonsList.checkedChipId.let {checkedId ->
                liveSort.value = when(binding.sortingButtonsList.findViewById<Chip>(checkedId).text) {
                    resources.getString(R.string.button_sort_alphabetical) -> {
                        Queries.QuerySort.Alphabetical
                    }
                    resources.getString(R.string.button_sort_reverse_alphabetical) -> {
                        Queries.QuerySort.ReverseAlphabetical
                    }
                    resources.getString(R.string.button_sort_most_popular) -> {
                        Queries.QuerySort.MostPopular
                    }
                    resources.getString(R.string.button_sort_least_popular) -> {
                        Queries.QuerySort.LeastPopular
                    }
                    resources.getString(R.string.button_sort_newest) -> {
                        Queries.QuerySort.LatestRelease
                    }
                    resources.getString(R.string.button_sort_oldest) -> {
                        Queries.QuerySort.OldestRelease
                    }
                    else -> {
                        Queries.QuerySort.Alphabetical
                    }
                }
            }
            submitQuery()
        }

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchBar.clearFocus()

                liveQuery.value = query
                submitQuery()
                return false
            }
        })

        _filteredAdapter = MoviesAdapter(this)
        binding.filteredMoviesList.adapter = adapter
        val layoutManager = GridLayoutManager(requireContext(), 3)
        layoutManager
        binding.filteredMoviesList.layoutManager = layoutManager

        viewModel.liveSearchMovies.observe(viewLifecycleOwner, {
            it?.let {
                adapter.updateData(it)
            }
        })

        return binding.root
    }

    fun displayMovieInfo(movie: MovieEntity) {
        viewModel.liveSelectedMovie.value = movie
        findNavController().navigate(R.id.movieInfoFragment)
    }

    fun submitQuery() {

        liveQuery.value?.let {search ->
            liveSort.value?.let {sort ->
                viewModel.searchMovies(search, sort)

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _filteredAdapter = null
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}