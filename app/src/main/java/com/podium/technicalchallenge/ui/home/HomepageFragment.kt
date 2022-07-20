package com.podium.technicalchallenge.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val horizontalLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        _top5Adapter = MoviesAdapter(this)
        binding.top5MoviesList.adapter = top5Adapter
        binding.top5MoviesList.layoutManager = horizontalLayoutManager

        viewModel.liveTop5Movies.observe(viewLifecycleOwner, {it ->
            it?.let {
                top5Adapter.updateData(it)
            }
        })

        viewModel.getTop5PopularMovies()

        viewModel.getMovies()
    }

    fun displayMovieInfo(movie: MovieEntity) {
        viewModel.liveSelectedMovie.value = movie
        findNavController().navigate(R.id.movieInfoFragment)
    }

        /*
    Requirements

    TODO:

    Priority

    1) Make a nav bar and viewpager item and do the 2 other sections
    2) Make the Genre Page/Tab Call it Discover (Search By Genre)

    - Make it like a button toggle group and find things by 1 genre


    3) Make the Search All Page (
    4) Get the recycler view to scroll down in the MovieInfoPage



    5) Add an action Bar
    6) Put a number on the side of the movie image in the Dashboard


    Create a view with the following sections:

    “Movies: Top 5”: Lists the top 5 movies of the data set, according to rating.
    “Browse by Genre”: Lists available genres.
    “Browse by All”: Lists available movies.

    Pressing a movie navigates to a detailed view of the movie. (DONE)

    Include title, rating, genres, poster, and description
    List the cast
    List the director
    Pressing a genre navigates to a new view showing the category and associated movies.

    The “Browse by All” and Genre view allows the user to sort the list of movies by an order of their choice (i.e. popularity).

    BONUS: Maybe add like a history of movies the user has clicked on and have that on the home screen

    BONUS: Show genres users were interested in on the home page
    */

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _top5Adapter = null // prevent memory leak
    }

    companion object {
        fun newInstance() = HomepageFragment()
    }
}