package com.podium.technicalchallenge.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.podium.technicalchallenge.DemoViewModel
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.databinding.FragmentHomeBinding
import com.podium.technicalchallenge.entity.MovieEntity
import com.podium.technicalchallenge.ui.dashboard.MoviesAdapter

class HomepageFragment : Fragment() {

    private val viewModel: DemoViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    var _top5Adapter: MoviesAdapter? = null
    private val top5Adapter get() = _top5Adapter!!

    var _recommendedAdapter: MoviesAdapter? = null
    private val recommendedAdapter get() = _recommendedAdapter!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val top5LayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        _top5Adapter = MoviesAdapter(this)
        binding.top5MoviesList.adapter = top5Adapter
        binding.top5MoviesList.layoutManager = top5LayoutManager

        val recLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        _recommendedAdapter = MoviesAdapter(this)
        binding.recommendedMoviesList.adapter = recommendedAdapter
        binding.recommendedMoviesList.layoutManager = recLayoutManager

        // Make a recommendation based on genre you clicked in the session
        // Get 10 Movies to show as a recommendation to the user
        viewModel.liveLastGenreClicked.observe(viewLifecycleOwner, {
            it?.let {genre ->
                val filteredMovies = viewModel.liveAllMovies.value?.filter { movie ->
                    movie.genres.contains(genre) && viewModel.liveTop5Movies.value?.contains(movie) == false
                }?.take(10)
                filteredMovies?.let { recommendedAdapter.updateData(it) }
            }
        })

        viewModel.liveTop5Movies.observe(viewLifecycleOwner, {
            it?.let {
                top5Adapter.updateData(it)
            }
        })

    }

    fun displayMovieInfo(movie: MovieEntity) {
        viewModel.liveSelectedMovie.value = movie
        findNavController().navigate(R.id.movieInfoFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _top5Adapter = null
    }

    companion object {
        fun newInstance() = HomepageFragment()
    }
}